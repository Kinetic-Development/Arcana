package net.arcanamod.commands;

public class CommandFocus{// extends CommandBase{
	/*@Override
	public String getName(){
		return "createfocus";
	}
	
	@Override
	public String getUsage(ICommandSender sender){
		return "createfocus <skin> <power> <core> <name> <effects>";
	}
	
	@Override
	public List<String> getAliases(){
		return new ArrayList<>(Arrays.asList("focus", "forgefocus"));
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException{
		if(sender instanceof PlayerEntity){
			PlayerEntity player = (PlayerEntity)sender;
			if(args.length > 5){
				try{
					List<ISpellEffect> effects = new ArrayList<>();
					for(int i = 5; i < args.length; i++){
						ISpellEffect effect = SpellEffectHandler.getEffect(args[i]);
						if(effect != null){
							effects.add(effect);
						}
					}
					player.inventory.addItemStackToInventory(FociHelper.generateFocus(Integer.parseInt(args[0]), effects.toArray(new ISpellEffect[0]), Integer.parseInt(args[1]), Aspect.valueOf(args[2]), args[3]));
					sender.sendMessage(new StringTextComponent(TextFormatting.GREEN + "Given a new focus!"));
				}catch(NumberFormatException ex){
					sender.sendMessage(new StringTextComponent(TextFormatting.RED + "One of the entered numbers is invalid!"));
				}
			}
		}
	}
	
	//Add node before final build
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender){
		return true;
	}
	
	@Override
	@Nonnull
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos){
		return Collections.emptyList();
	}
	*/
}
