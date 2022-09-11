package io.github.adex720.wildmagic.raycast;

import net.minecraft.entity.Entity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Result of a raycast.
 *
 * @author adex720
 */
public class RaycastResult {

    @Nonnull
    private final HitResult.Type type;

    @Nullable
    private final BlockPos lastBlockPos;
    @Nullable
    private final Entity lastEntity;
    @Nullable
    private final Vec3d pos;

    private RaycastResult(@Nonnull HitResult.Type type, @Nullable BlockPos blockPos, @Nullable Entity entity, @Nullable Vec3d pos) {
        this.type = type;
        this.lastBlockPos = blockPos;
        this.lastEntity = entity;
        this.pos = pos;
    }

    public static RaycastResult miss() {
        return new RaycastResult(HitResult.Type.MISS, null, null, null);
    }

    public static RaycastResult block(@Nonnull BlockPos blockPos, @Nonnull Vec3d hitPos) {
        return new RaycastResult(HitResult.Type.BLOCK, blockPos, null, hitPos);
    }

    public static RaycastResult entity(@Nonnull Entity entity, @Nonnull Vec3d hitPos) {
        return new RaycastResult(HitResult.Type.ENTITY, null, entity, hitPos);
    }

    @Nonnull
    public HitResult.Type getType() {
        return type;
    }

    @Nullable
    public BlockPos getBlockPos() {
        return lastBlockPos;
    }

    @Nullable
    public Entity getEntity() {
        return lastEntity;
    }

    /**
     * Returns the position where the hit landed.
     */
    @Nullable
    public Vec3d getHitPos() {
        return pos;
    }

    /**
     * Returns the position of the last target.
     */
    @Nullable
    public Vec3d getTargetPos() {
        if (lastBlockPos != null) return new Vec3d(lastBlockPos.getX(), lastBlockPos.getY(), lastBlockPos.getZ());
        if (lastEntity != null) return lastEntity.getPos();
        return null;
    }

    /**
     * Returns the fixed position of the last target.
     * If the target was a block, the position will be moved to the center of the block and raised on top of it.
     * If the target was an entity, the position will be moved up half the height of the entity.
     */
    @Nullable
    public Vec3d getFixedPos() {
        if (lastBlockPos != null)
            return new Vec3d(lastBlockPos.getX() + 0.5d, lastBlockPos.getY() + 1.25d, lastBlockPos.getZ() + 0.5d);
        if (lastEntity != null) return lastEntity.getPos().add(0, lastEntity.getHeight() * 0.5f, 0);
        return null;
    }
}
