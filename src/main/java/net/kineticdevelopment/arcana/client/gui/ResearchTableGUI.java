package net.kineticdevelopment.arcana.client.gui;

import net.kineticdevelopment.arcana.common.containers.ResearchTableContainer;
import net.kineticdevelopment.arcana.common.objects.tile.ResearchTableTileEntity;
import net.kineticdevelopment.arcana.core.Main;
import net.kineticdevelopment.arcana.core.aspects.Aspect;
import net.kineticdevelopment.arcana.core.aspects.AspectHandler;
import net.kineticdevelopment.arcana.core.aspects.Aspects;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ResearchTableGUI extends GuiAspectContainer{
	
	public static final int WIDTH = 376;
	public static final int HEIGHT = 280;
	
	private static final ResourceLocation bg = new ResourceLocation(Main.MODID, "textures/gui/container/gui_researchbook.png");
	
	ResearchTableTileEntity te;
	protected List<AspectSlot> scrollableSlots = new ArrayList<>();
	
	public ResearchTableGUI(ResearchTableTileEntity te, ResearchTableContainer container){
		super(container);
		this.te = te;
		xSize = WIDTH;
		ySize = HEIGHT;
	}
	
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		mc.getTextureManager().bindTexture(bg);
		drawModalRectWithCustomSizedTexture(guiLeft, guiTop, 0, 0, WIDTH, HEIGHT, 378, 378);
	}
	
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}
	
	protected void initSlots(){
		Supplier<AspectHandler> aspects = () -> AspectHandler.getFrom(te.visItem());
		for(int i = 0; i < Aspects.primalAspects.length; i++){
			Aspect primal = Aspects.primalAspects[i];
			int x = 31 + 16 * i;
			int y = 14;
			if(i % 2 == 0)
				y += 5;
			aspectSlots.add(new AspectSlot(primal, aspects, x, y));
		}
		Aspect[] values = Aspect.values();
		Supplier<AspectHandler> table = () -> AspectHandler.getFrom(te);
		for(int i = 0; i < values.length; i++){
			Aspect aspect = values[i];
			int yy = i / 6;
			int xx = i % 6;
			int x = 9 + 20 * xx;
			int y = 65 + 21 * yy;
			// the scroll wheel handles all of them
			if(yy >= 5)
				break;
			if(xx % 2 == 0)
				y += 5;
			if(yy % 2 == 0)
				x += 3;
			AspectSlot slot = new AspectSlot(aspect, table, x, y);
			aspectSlots.add(slot);
			scrollableSlots.add(slot);
		}
	}
}