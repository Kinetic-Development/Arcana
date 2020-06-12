package net.arcanamod.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IProjectile;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;

/**
 * Spell Projectile Entity
 *
 * @author Merijn
 */
public class SpellEntity extends Entity implements IProjectile{
	
	public SpellEntity(EntityType<?> entityTypeIn, World worldIn){
		super(entityTypeIn, worldIn);
	}
	
	public SpellEntity(World worldIn){
		super(null, worldIn);
	}
	
	/*private Spell spell;
	private LivingEntity caster;
	
	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy){
		float f = MathHelper.sqrt(x * x + y * y + z * z);
		x = x / (double)f;
		y = y / (double)f;
		z = z / (double)f;
		x = x + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
		y = y + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
		z = z + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
		x = x * (double)velocity;
		y = y * (double)velocity;
		z = z * (double)velocity;
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		float f1 = MathHelper.sqrt(x * x + z * z);
		this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
		this.rotationPitch = (float)(MathHelper.atan2(y, f1) * (180D / Math.PI));
		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;
	}
	
	@Nullable
	private Entity getHitEntity(Vec3d p_190538_1_, Vec3d p_190538_2_){
		Entity entity = null;
		List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0D));
		double d0 = 0.0D;
		
		for(Entity entity1 : list){
			if(entity1 != this.caster){
				AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(0.30000001192092896D);
				RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(p_190538_1_, p_190538_2_);
				
				if(raytraceresult != null){
					double d1 = p_190538_1_.squareDistanceTo(raytraceresult.hitVec);
					
					if(d1 < d0 || d0 == 0.0D){
						entity = entity1;
						d0 = d1;
					}
				}
			}
		}
		
		return entity;
	}
	
	@Override
	public void onUpdate(){
		super.onUpdate();
		//Minecraft.getMinecraft().player.getEntityWorld().spawnParticle(EnumParticleTypes.FLAME, this.getPositionVector().x, this.getPositionVector().y, this.getPositionVector().z, 0, 0,0);
		this.getEntityWorld().spawnParticle(EnumParticleTypes.FLAME, this.getPositionVector().x, this.getPositionVector().y, this.getPositionVector().z, 0, 0, 0);
		
		Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);
		Vec3d vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d, vec3d1);
		vec3d = new Vec3d(this.posX, this.posY, this.posZ);
		vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		
		if(raytraceresult != null){
			vec3d1 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
		}
		
		Entity entity = this.getHitEntity(vec3d, vec3d1);
		
		if(entity != null){
			raytraceresult = new RayTraceResult(entity);
		}
		
		if(raytraceresult != null && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)){
			this.onHit(raytraceresult);
		}
		
		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));
		
		for(this.rotationPitch = (float)(MathHelper.atan2(this.motionY, f) * (180D / Math.PI)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F){
		}
		
		while(this.rotationPitch - this.prevRotationPitch >= 180.0F){
			this.prevRotationPitch += 360.0F;
		}
		
		while(this.rotationYaw - this.prevRotationYaw < -180.0F){
			this.prevRotationYaw -= 360.0F;
		}
		
		while(this.rotationYaw - this.prevRotationYaw >= 180.0F){
			this.prevRotationYaw += 360.0F;
		}
		
		this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
		this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
		@SuppressWarnings("unused")
		float f1 = 0.99F;
		@SuppressWarnings("unused")
		float f2 = 0.06F;
		
		if(!this.world.isMaterialInBB(this.getEntityBoundingBox(), Material.AIR)){
			this.setDead();
		}else if(this.isInWater()){
			this.setDead();
		}else{
			this.motionX *= 0.9900000095367432D;
			this.motionY *= 0.9900000095367432D;
			this.motionZ *= 0.9900000095367432D;
			
			if(!this.hasNoGravity()){
				this.motionY -= 0.05999999865889549D;
			}
			
			this.setPosition(this.posX, this.posY, this.posZ);
		}
	}
	
	@Override
	public void writeEntityToNBT(CompoundNBT compound){
		CompoundNBT tag = new CompoundNBT();
		
		StringBuilder sb = new StringBuilder();
		for(ISpellEffect effect : spell.getEffects()){
			sb.append(effect.getName()).append(";");
		}
		
		tag.setString("effects", sb.toString());
		tag.setInteger("power", spell.getPower());
		tag.setString("core", spell.getCore().toString());
		tag.setString("name", spell.getName());
		compound.setTag("foci", tag);
		compound.setUniqueId("caster", caster.getUniqueID());
	}
	
	@Override
	public void readEntityFromNBT(CompoundNBT compound){
		this.spell = Spell.fromNBT(compound.getCompoundTag("foci"));
		this.caster = findPlayer(this.getEntityWorld(), compound.getUniqueId("caster"));
	}
	
	public static PlayerEntity findPlayer(World world, UUID uuid){
		return *//*!world.isRemote ? Minecraft.getMinecraft().player :*//* Objects.requireNonNull(world.getMinecraftServer()).getPlayerList().getPlayerByUUID(uuid); // The Getter when the Player is on a Server
	}
	
	public void onHit(RayTraceResult result){
		if(result.typeOfHit == RayTraceResult.Type.BLOCK){
			for(ISpellEffect effect : spell.getEffects()){
				effect.getEffect(result.getBlockPos(), getEntityWorld(), spell.getPower());
			}
		}else if(result.typeOfHit == RayTraceResult.Type.ENTITY){
			for(ISpellEffect effect : spell.getEffects()){
				if(result.entityHit instanceof LivingEntity){
					effect.getEffect((LivingEntity)result.entityHit, spell.getPower());
				}
			}
		}
	}*/
	
	protected void registerData(){
	
	}
	
	protected void readAdditional(CompoundNBT compound){
	
	}
	
	protected void writeAdditional(CompoundNBT compound){
	
	}
	
	public IPacket<?> createSpawnPacket(){
		return null;
	}
	
	public void shoot(double x, double y, double z, float velocity, float inaccuracy){
	
	}
}
