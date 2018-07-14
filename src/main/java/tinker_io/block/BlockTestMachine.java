package tinker_io.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.Sys;
import tinker_io.TinkerIO;
import tinker_io.registry.GuiRegistry;
import tinker_io.tileentity.TileEntityTestMachine;

import javax.annotation.Nullable;

public class BlockTestMachine extends BlockTileEntity<TileEntityTestMachine> {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockTestMachine(String name) {
        super(Material.ROCK, name);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public Class<TileEntityTestMachine> getTileEntityClass() {
        return TileEntityTestMachine.class;
    }

    @Nullable
    @Override
    public TileEntityTestMachine createTileEntity(World world, IBlockState state) {
        return new TileEntityTestMachine();
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getFront(meta & 7);
        if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }
        return getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        this.setDefaultFacing(worldIn, pos, state);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntityTestMachine tile = getTileEntity(world, pos);
        IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        ItemStack stack = itemHandler.getStackInSlot(0);
        if (!stack.isEmpty()) {
            EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            world.spawnEntity(item);
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            if (player.isSneaking()) {
                return false;
            } else {
                player.openGui(TinkerIO.instance, GuiRegistry.TEST_MACHINE, world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            IBlockState blockStateNorth = worldIn.getBlockState(pos.north());
            IBlockState blockStateSouth = worldIn.getBlockState(pos.south());
            IBlockState blockStateWest = worldIn.getBlockState(pos.west());
            IBlockState blockStateEast = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && blockStateNorth.isFullBlock() && !blockStateSouth.isFullBlock()) {
                enumfacing = EnumFacing.SOUTH;
            } else if (enumfacing == EnumFacing.SOUTH && blockStateSouth.isFullBlock() && !blockStateNorth.isFullBlock()) {
                enumfacing = EnumFacing.NORTH;
            } else if (enumfacing == EnumFacing.WEST && blockStateWest.isFullBlock() && !blockStateEast.isFullBlock()) {
                enumfacing = EnumFacing.EAST;
            } else if (enumfacing == EnumFacing.EAST && blockStateEast.isFullBlock() && !blockStateWest.isFullBlock()) {
                enumfacing = EnumFacing.WEST;
            }
            System.out.println(enumfacing);
            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }
}
