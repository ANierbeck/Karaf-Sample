package de.inovex.javamagazin.jpa.command;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.commands.Option;
import org.apache.karaf.shell.console.OsgiCommandSupport;

import de.inovex.javamagazin.jpa.broker.InventoryEntityBroker;

@Command(scope = "javamagazin", name = "DeleteCategory", description = "CRUD commands for the JavaMagazin samples")
public class DeleteCategory extends OsgiCommandSupport {

	@Argument(name = "name", description = "Name of Category", required = false, multiValued = false)
	private String name;
	
	//name = "-d", aliases = { "--description" }
	@Option(name = "-id", description = "ID of the Category", required = false, multiValued = false)
	private String id; 

	private InventoryEntityBroker broker;

	public void setBroker(InventoryEntityBroker broker) {
		this.broker = broker;
	}

	protected Object doExecute() throws Exception {

		if (name != null) {
			broker.deleteCategoryByName(name);
		} else if (id != null) {
			broker.deleteCategory(Integer.parseInt(id));
		}

		return null;
	}
}
