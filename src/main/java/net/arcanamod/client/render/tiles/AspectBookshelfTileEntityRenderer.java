package net.arcanamod.client.render.tiles;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.arcanamod.Arcana;
import net.arcanamod.aspects.Aspects;
import net.arcanamod.blocks.tiles.AspectBookshelfTileEntity;
import net.arcanamod.items.PhialItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.awt.*;

public class AspectBookshelfTileEntityRenderer extends TileEntityRenderer<AspectBookshelfTileEntity> {
    public static final ResourceLocation PHIAL_LID = new ResourceLocation(Arcana.MODID, "models/aspect_bookshelf/phial_lid");
    public static final ResourceLocation PHIAL_BODY = new ResourceLocation(Arcana.MODID, "models/aspect_bookshelf/phial_body");
    public static final ResourceLocation PHIAL_BASE = new ResourceLocation(Arcana.MODID, "models/aspect_bookshelf/phial_base");
    public static final ResourceLocation PHIAL_TOP = new ResourceLocation(Arcana.MODID, "models/aspect_bookshelf/phial_top");
    public static final ResourceLocation PHIAL_SIDE = new ResourceLocation(Arcana.MODID, "models/aspect_bookshelf/phial_side");
    public static final ResourceLocation PHIAL_BOTTOM = new ResourceLocation(Arcana.MODID, "models/aspect_bookshelf/phial_bottom");
    public static final ResourceLocation PHIAL_CAP = new ResourceLocation(Arcana.MODID, "models/aspect_bookshelf/phial_cap");

    public AspectBookshelfTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    private void add(IVertexBuilder renderer, MatrixStack stack, Color color, float x, float y, float z, float u, float v, int lightmap){
        renderer.pos(stack.getLast().getMatrix(), x, y, z)
                .color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f)
                .tex(u, v)
                .lightmap(lightmap)
                .normal(1, 0, 0)
                .endVertex();
    }

    @Override
    public void render(AspectBookshelfTileEntity tileEntity, float partialTicks, @Nonnull MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        TextureAtlasSprite spriteLid = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(PHIAL_LID);
        TextureAtlasSprite spriteBody = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(PHIAL_BODY);
        TextureAtlasSprite spriteBase = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(PHIAL_BASE);
        TextureAtlasSprite spriteTop = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(PHIAL_TOP);
        TextureAtlasSprite spriteSide = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(PHIAL_SIDE);
        TextureAtlasSprite spriteBottom = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(PHIAL_BOTTOM);
        TextureAtlasSprite spriteCap = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(PHIAL_CAP);
        IVertexBuilder builder = buffer.getBuffer(RenderType.getTranslucent());

        Quaternion q = Quaternion.ONE;
        switch (tileEntity.rotation) {
            case NORTH:
                q = new Quaternion(0, 0, 0, true);
                break;
            case WEST:
                q = new Quaternion(0, 90, 0, true);
                break;
            case SOUTH:
                q = new Quaternion(0, 180, 0, true);
                break;
            case EAST:
                q = new Quaternion(0, 270, 0, true);
                break;
        }
        matrixStack.translate(.5, 0, .5);
        matrixStack.rotate(q);
        matrixStack.translate(-.5, 0, -.5);


        for (int i = 0; i <= 8; i++) {
            if (tileEntity.getStackInSlot(i).getItem() instanceof PhialItem) {
                matrixStack.push();
                int slotX = 2 - ((i) % 3);
                int slotY = 2 - ((i) / 3);

                Color colour = PhialItem.getAspect(tileEntity.getStackInSlot(i)) != Aspects.EMPTY ?
                        new Color(PhialItem.getAspect(tileEntity.getStackInSlot(i)).getColorRange().get(2)) : Color.WHITE;
                Color c = new Color(colour.getRGB()-0x80000000);

                //Base
                add(builder, matrixStack, c, 0.3125f + (0.3125f * slotX), 0.0625f + (0.3125f * slotY), 0.5625f, spriteBase.getMaxU(), spriteBase.getMaxV(), combinedLight);
                add(builder, matrixStack, c, 0.0625f + (0.3125f * slotX), 0.0625f + (0.3125f * slotY), 0.5625f, spriteBase.getMinU(), spriteBase.getMaxV(), combinedLight);
                add(builder, matrixStack, c, 0.0625f + (0.3125f * slotX), 0.3125f + (0.3125f * slotY), 0.5625f, spriteBase.getMinU(), spriteBase.getMinV(), combinedLight);
                add(builder, matrixStack, c, 0.3125f + (0.3125f * slotX), 0.3125f + (0.3125f * slotY), 0.5625f, spriteBase.getMaxU(), spriteBase.getMinV(), combinedLight);

                //Front Handle
                add(builder, matrixStack, c, 0.28125f + (0.3125f * slotX), 0.09375f + (0.3125f * slotY), 0.4375f, spriteLid.getMaxU(), spriteLid.getMaxV(), combinedLight);
                add(builder, matrixStack, c, 0.09375f + (0.3125f * slotX), 0.09375f + (0.3125f * slotY), 0.4375f, spriteLid.getMinU(), spriteLid.getMaxV(), combinedLight);
                add(builder, matrixStack, c, 0.09375f + (0.3125f * slotX), 0.28125f + (0.3125f * slotY), 0.4375f, spriteLid.getMinU(), spriteLid.getMinV(), combinedLight);
                add(builder, matrixStack, c, 0.28125f + (0.3125f * slotX), 0.28125f + (0.3125f * slotY), 0.4375f, spriteLid.getMaxU(), spriteLid.getMinV(), combinedLight);

                //Front Cap
                add(builder, matrixStack, Color.WHITE, 0.28125f + (0.3125f * slotX), 0.09375f + (0.3125f * slotY), 0.4375f, spriteCap.getMaxU(), spriteCap.getMaxV(), combinedLight);
                add(builder, matrixStack, Color.WHITE, 0.09375f + (0.3125f * slotX), 0.09375f + (0.3125f * slotY), 0.4375f, spriteCap.getMinU(), spriteCap.getMaxV(), combinedLight);
                add(builder, matrixStack, Color.WHITE, 0.09375f + (0.3125f * slotX), 0.28125f + (0.3125f * slotY), 0.4375f, spriteCap.getMinU(), spriteCap.getMinV(), combinedLight);
                add(builder, matrixStack, Color.WHITE, 0.28125f + (0.3125f * slotX), 0.28125f + (0.3125f * slotY), 0.4375f, spriteCap.getMaxU(), spriteCap.getMinV(), combinedLight);

                //Back Handle
                add(builder, matrixStack, c, 0.09375f + (0.3125f * slotX), 0.09375f + (0.3125f * slotY), 0.5000f, spriteBody.getMaxU(), spriteBody.getMaxV(), combinedLight);
                add(builder, matrixStack, c, 0.28125f + (0.3125f * slotX), 0.09375f + (0.3125f * slotY), 0.5000f, spriteBody.getMinU(), spriteBody.getMaxV(), combinedLight);
                add(builder, matrixStack, c, 0.28125f + (0.3125f * slotX), 0.28125f + (0.3125f * slotY), 0.5000f, spriteBody.getMinU(), spriteBody.getMinV(), combinedLight);
                add(builder, matrixStack, c, 0.09375f + (0.3125f * slotX), 0.28125f + (0.3125f * slotY), 0.5000f, spriteBody.getMaxU(), spriteBody.getMinV(), combinedLight);

                //Left Handle
                add(builder, matrixStack, c, 0.28125f + (0.3125f * slotX), 0.09375f + (0.3125f * slotY), 0.5000f, spriteSide.getMaxU(), spriteSide.getMaxV(), combinedLight);
                add(builder, matrixStack, c, 0.28125f + (0.3125f * slotX), 0.09375f + (0.3125f * slotY), 0.4375f, spriteSide.getMinU(), spriteSide.getMaxV(), combinedLight);
                add(builder, matrixStack, c, 0.28125f + (0.3125f * slotX), 0.28125f + (0.3125f * slotY), 0.4375f, spriteSide.getMinU(), spriteSide.getMinV(), combinedLight);
                add(builder, matrixStack, c, 0.28125f + (0.3125f * slotX), 0.28125f + (0.3125f * slotY), 0.5000f, spriteSide.getMaxU(), spriteSide.getMinV(), combinedLight);

                //Right Handle
                add(builder, matrixStack, c, 0.09375f + (0.3125f * slotX), 0.28125f + (0.3125f * slotY), 0.5000f, spriteSide.getMaxU(), spriteSide.getMinV(), combinedLight);
                add(builder, matrixStack, c, 0.09375f + (0.3125f * slotX), 0.28125f + (0.3125f * slotY), 0.4375f, spriteSide.getMinU(), spriteSide.getMinV(), combinedLight);
                add(builder, matrixStack, c, 0.09375f + (0.3125f * slotX), 0.09375f + (0.3125f * slotY), 0.4375f, spriteSide.getMinU(), spriteSide.getMaxV(), combinedLight);
                add(builder, matrixStack, c, 0.09375f + (0.3125f * slotX), 0.09375f + (0.3125f * slotY), 0.5000f, spriteSide.getMaxU(), spriteSide.getMaxV(), combinedLight);

                //Top Handle
                add(builder, matrixStack, c, 0.28125f + (0.3125f * slotX), 0.28125f + (0.3125f * slotY), 0.4375f, spriteTop.getMaxU(), spriteTop.getMaxV(), combinedLight);
                add(builder, matrixStack, c, 0.09375f + (0.3125f * slotX), 0.28125f + (0.3125f * slotY), 0.4375f, spriteTop.getMinU(), spriteTop.getMaxV(), combinedLight);
                add(builder, matrixStack, c, 0.09375f + (0.3125f * slotX), 0.28125f + (0.3125f * slotY), 0.5000f, spriteTop.getMinU(), spriteTop.getMinV(), combinedLight);
                add(builder, matrixStack, c, 0.28125f + (0.3125f * slotX), 0.28125f + (0.3125f * slotY), 0.5000f, spriteTop.getMaxU(), spriteTop.getMinV(), combinedLight);

                //Bottom Handle
                add(builder, matrixStack, c, 0.09375f + (0.3125f * slotX), 0.09375f + (0.3125f * slotY), 0.4375f, spriteBottom.getMaxU(), spriteBottom.getMaxV(), combinedLight);
                add(builder, matrixStack, c, 0.28125f + (0.3125f * slotX), 0.09375f + (0.3125f * slotY), 0.4375f, spriteBottom.getMinU(), spriteBottom.getMaxV(), combinedLight);
                add(builder, matrixStack, c, 0.28125f + (0.3125f * slotX), 0.09375f + (0.3125f * slotY), 0.5000f, spriteBottom.getMinU(), spriteBottom.getMinV(), combinedLight);
                add(builder, matrixStack, c, 0.09375f + (0.3125f * slotX), 0.09375f + (0.3125f * slotY), 0.5000f, spriteBottom.getMaxU(), spriteBottom.getMinV(), combinedLight);

                //Left Base
                add(builder, matrixStack, c, 0.25f + (0.3125f * slotX), 0.125f + (0.3125f * slotY), 0.5625f, spriteBody.getMaxU(), spriteBody.getMaxV(), combinedLight);
                add(builder, matrixStack, c, 0.25f + (0.3125f * slotX), 0.125f + (0.3125f * slotY), 0.5000f, spriteBody.getMinU(), spriteBody.getMaxV(), combinedLight);
                add(builder, matrixStack, c, 0.25f + (0.3125f * slotX), 0.25f + (0.3125f * slotY), 0.5000f, spriteBody.getMinU(), spriteBody.getMinV(), combinedLight);
                add(builder, matrixStack, c, 0.25f + (0.3125f * slotX), 0.25f + (0.3125f * slotY), 0.5625f, spriteBody.getMaxU(), spriteBody.getMinV(), combinedLight);

                //Right Base
                add(builder, matrixStack, c, 0.125f + (0.3125f * slotX), 0.25f + (0.3125f * slotY), 0.5625f, spriteBody.getMaxU(), spriteBody.getMinV(), combinedLight);
                add(builder, matrixStack, c, 0.125f + (0.3125f * slotX), 0.25f + (0.3125f * slotY), 0.5000f, spriteBody.getMinU(), spriteBody.getMinV(), combinedLight);
                add(builder, matrixStack, c, 0.125f + (0.3125f * slotX), 0.125f + (0.3125f * slotY), 0.5000f, spriteBody.getMinU(), spriteBody.getMaxV(), combinedLight);
                add(builder, matrixStack, c, 0.125f + (0.3125f * slotX), 0.125f + (0.3125f * slotY), 0.5625f, spriteBody.getMaxU(), spriteBody.getMaxV(), combinedLight);

                //Top Base
                add(builder, matrixStack, c, 0.25f + (0.3125f * slotX), 0.25f + (0.3125f * slotY), 0.5000f, spriteBody.getMaxU(), spriteBody.getMaxV(), combinedLight);
                add(builder, matrixStack, c, 0.125f + (0.3125f * slotX), 0.25f + (0.3125f * slotY), 0.5000f, spriteBody.getMinU(), spriteBody.getMaxV(), combinedLight);
                add(builder, matrixStack, c, 0.125f + (0.3125f * slotX), 0.25f + (0.3125f * slotY), 0.5625f, spriteBody.getMinU(), spriteBody.getMinV(), combinedLight);
                add(builder, matrixStack, c, 0.25f + (0.3125f * slotX), 0.25f + (0.3125f * slotY), 0.5625f, spriteBody.getMaxU(), spriteBody.getMinV(), combinedLight);

                //Bottom Base
                add(builder, matrixStack, c, 0.125f + (0.3125f * slotX), 0.125f + (0.3125f * slotY), 0.5000f, spriteBody.getMaxU(), spriteBody.getMaxV(), combinedLight);
                add(builder, matrixStack, c, 0.25f + (0.3125f * slotX), 0.125f + (0.3125f * slotY), 0.5000f, spriteBody.getMinU(), spriteBody.getMaxV(), combinedLight);
                add(builder, matrixStack, c, 0.25f + (0.3125f * slotX), 0.125f + (0.3125f * slotY), 0.5625f, spriteBody.getMinU(), spriteBody.getMinV(), combinedLight);
                add(builder, matrixStack, c, 0.125f + (0.3125f * slotX), 0.125f + (0.3125f * slotY), 0.5625f, spriteBody.getMaxU(), spriteBody.getMinV(), combinedLight);

                matrixStack.pop();
            }
        }
    }
}
