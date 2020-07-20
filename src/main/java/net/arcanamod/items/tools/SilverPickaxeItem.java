package net.arcanamod.items.tools;

import mcp.MethodsReturnNonnullByDefault;
import net.arcanamod.blocks.Taint;
import net.minecraft.block.BlockState;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SilverPickaxeItem extends PickaxeItem{
	
	public SilverPickaxeItem(IItemTier tier, int attackDamage, float attackSpeed, Properties builder){
		super(tier, attackDamage, attackSpeed, builder);
	}
	
	public float getDestroySpeed(ItemStack stack, BlockState state){
		float speed = super.getDestroySpeed(stack, state);
		return Taint.isTainted(state.getBlock()) ? speed * 2 : speed;
	}
}