package io.github.adex720.wildmagic.raycast;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.RaycastContext;

/**
 * Helps to locate the target a player is looking at. The target can be an entity, a block or none.
 * If the target is too far away, none is selected.
 * <p>
 * Huge thanks for the Fabric Wiki tutorial on pixel raycasting.
 *
 * @author adex720
 * @see <a href="https://fabricmc.net/wiki/tutorial:pixel_raycast"></a>
 */
public class RaycastHelper {

    private BlockPos lastBlockPos;
    private Entity lastEntity;

    public RaycastHelper() {
        lastBlockPos = null;
        lastEntity = null;
    }

    public RaycastResult raycast(MinecraftClient client, int tickDelta, double reachDistance) {
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();
        Vec3d cameraDirection = client.cameraEntity.getRotationVec(tickDelta);
        double fov = client.options.getFov().getValue();
        double angleSize = fov / height;
        Vec3f verticalRotationAxis = new Vec3f(cameraDirection);
        verticalRotationAxis.cross(Vec3f.POSITIVE_Y);
        if (!verticalRotationAxis.normalize()) {
            // TODO: check for blocks
            return RaycastResult.miss();
        }

        Vec3f horizontalRotationAxis = new Vec3f(cameraDirection);
        horizontalRotationAxis.cross(verticalRotationAxis);
        horizontalRotationAxis.normalize();

        verticalRotationAxis = new Vec3f(cameraDirection);
        verticalRotationAxis.cross(horizontalRotationAxis);

        Vec3d direction = map((float) angleSize, cameraDirection, horizontalRotationAxis, verticalRotationAxis, (int) (width * 0.5f), (int) (height * 0.5f), width, height);
        HitResult hit = raycastInDirection(client, tickDelta, direction, reachDistance);
        if (hit == null) return RaycastResult.miss();

        switch (hit.getType()) {
            case BLOCK -> {
                BlockHitResult blockHit = (BlockHitResult) hit;
                return RaycastResult.block(blockHit.getBlockPos(), hit.getPos());
            }
            case ENTITY -> {
                EntityHitResult entityHit = (EntityHitResult) hit;
                return RaycastResult.entity(entityHit.getEntity(), hit.getPos());
            }
        }
        return RaycastResult.miss();
    }

    private static Vec3d map(float anglePerPixel, Vec3d center, Vec3f horizontalRotationAxis,
                             Vec3f verticalRotationAxis, int x, int y, int width, int height) {
        float horizontalRotation = (x - width / 2f) * anglePerPixel;
        float verticalRotation = (y - height / 2f) * anglePerPixel;

        final Vec3f temp2 = new Vec3f(center);
        temp2.rotate(verticalRotationAxis.getDegreesQuaternion(verticalRotation));
        temp2.rotate(horizontalRotationAxis.getDegreesQuaternion(horizontalRotation));
        return new Vec3d(temp2);
    }

    private static HitResult raycastInDirection(MinecraftClient client, float tickDelta, Vec3d direction, double reachDistance) {
        Entity cameraEntity = client.getCameraEntity();
        if (cameraEntity == null || client.world == null) return null;

        HitResult target = raycast(cameraEntity, reachDistance, tickDelta, false, direction);
        Vec3d cameraPos = cameraEntity.getCameraPosVec(tickDelta);

        reachDistance = reachDistance * reachDistance;
        if (target != null) reachDistance = target.getPos().squaredDistanceTo(cameraPos);

        Vec3d vec3d3 = cameraPos.add(direction.multiply(reachDistance));
        Box box = cameraEntity.getBoundingBox().stretch(cameraEntity.getRotationVec(1.0F).multiply(reachDistance)).expand(1.0D, 1.0D, 1.0D);
        EntityHitResult entityHitResult = ProjectileUtil.raycast(cameraEntity, cameraPos, vec3d3, box, (entityx) -> !entityx.isSpectator(), reachDistance);

        if (entityHitResult == null) return target;

        Vec3d entityPos = entityHitResult.getPos();
        double entityDistance = cameraPos.squaredDistanceTo(entityPos);
        if (entityDistance < reachDistance || target == null) {
            target = entityHitResult;
        }

        return target;
    }

    private static HitResult raycast(Entity entity, double maxDistance, float tickDelta, boolean includeFluids, Vec3d direction) {
        Vec3d end = entity.getCameraPosVec(tickDelta).add(direction.multiply(maxDistance));
        return entity.world.raycast(new RaycastContext(entity.getCameraPosVec(tickDelta), end, RaycastContext.ShapeType.OUTLINE,
                includeFluids ? RaycastContext.FluidHandling.ANY : RaycastContext.FluidHandling.NONE, entity));
    }

}
