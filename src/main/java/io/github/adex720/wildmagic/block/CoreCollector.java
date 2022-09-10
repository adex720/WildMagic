package io.github.adex720.wildmagic.block;

import io.github.adex720.wildmagic.enchantments.EnduranceEnchantment;
import io.github.adex720.wildmagic.harvest.HarvestTool;
import io.github.adex720.wildmagic.registry.ModEnchantments;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/**
 * A core collector block slowly collects cores used when crafting wands.
 * The collecting process is only active when the correct block is placed on top of the block.
 *
 * @author adex720
 */
public class CoreCollector extends Block {

    public static final BooleanProperty FERTILE = BooleanProperty.of("fertile");
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");

    public final Block blockOnTop;
    public final HarvestTool harvestTool;
    public final Item harvestItem;
    public final int fertilizationDifficulty;

    /**
     * @param settings                Settings, {@link FabricBlockSettings#ticksRandomly()} is called inside constructor.
     * @param blockOnTop              Block required to be on top of this in order to fertilization to be possible.
     * @param harvestTool             Item used to harvest from this block.
     * @param harvestItem             Item given to player on harvest.
     * @param fertilizationDifficulty Change of block getting fertile on random tick. (1/difficulty)
     */
    public CoreCollector(FabricBlockSettings settings, Block blockOnTop, HarvestTool harvestTool, Item harvestItem, int fertilizationDifficulty) {
        super(settings.ticksRandomly());
        setDefaultState(getStateManager().getDefaultState().with(FERTILE, false).with(ACTIVE, false));

        this.blockOnTop = blockOnTop;
        this.harvestTool = harvestTool;
        this.harvestItem = harvestItem;
        this.fertilizationDifficulty = fertilizationDifficulty;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FERTILE).add(ACTIVE);
    }

    /**
     * Checks if the collector is active.
     */
    private void updateActive(World world, BlockPos blockPos, BlockState state) {
        world.setBlockState(blockPos, state.with(ACTIVE, world.getBlockState(blockPos.up()).getBlock() == blockOnTop));
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (sourcePos.getY() - 1 == pos.getY()) updateActive(world, pos, world.getBlockState(pos));
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    }

    private void makeFertile(World world, BlockPos pos) {
        world.setBlockState(pos, world.getBlockState(pos).with(FERTILE, true));
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!world.getBlockState(pos).get(ACTIVE)) return;
        if (random.nextInt(fertilizationDifficulty) == 0) makeFertile(world, pos);
    }


    /**
     * Harvest a core from the block and gives it to the harvester.
     *
     * @param world       World
     * @param player      Harvester
     * @param pos         Position of the collector
     * @param state       Current state of the collector
     * @param harvestTool Item used on harvest
     */
    private void harvest(World world, PlayerEntity player, BlockPos pos, BlockState state, ItemStack harvestTool) {
        ItemEntity itemEntity = player.dropItem(new ItemStack(harvestItem, 1), false);
        itemEntity.resetPickupDelay();
        itemEntity.setOwner(player.getUuid());

        int endurance = EnchantmentHelper.getLevel(ModEnchantments.ENDURANCE, harvestTool);
        if (!EnduranceEnchantment.shouldPreventDamage(endurance, world.getRandom())) {
            ServerPlayerEntity serverPlayer = world.isClient ? null : (ServerPlayerEntity) player;
            if (harvestTool.damage(1, world.getRandom(), serverPlayer)) harvestTool.setCount(0);
        }

        player.playSound(SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, 1, 1); //TODO: create sound
        world.setBlockState(pos, state.with(FERTILE, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack item = hand == Hand.MAIN_HAND ? player.getMainHandStack() : player.getOffHandStack();
        if (item.getItem() != harvestTool) return ActionResult.PASS;
        if (!world.getBlockState(pos).get(FERTILE)) return ActionResult.SUCCESS;

        harvest(world, player, pos, state, item);

        return ActionResult.CONSUME;
    }
}
