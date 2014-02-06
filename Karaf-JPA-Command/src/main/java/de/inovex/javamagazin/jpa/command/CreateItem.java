
package de.inovex.javamagazin.jpa.command;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.commands.Option;
import org.apache.karaf.shell.console.OsgiCommandSupport;

import de.inovex.javamagazin.jpa.InventoryCategory;
import de.inovex.javamagazin.jpa.broker.InventoryEntityBroker;

@Command(scope = "javamagazin", name = "CreateItem", description = "CRUD commands for the JavaMagazin samples")
public class CreateItem extends OsgiCommandSupport {

    @Option(name = "-d", aliases = { "--description" }, description = "A description to the item", required = false, multiValued = false)
    private String description;
    
    @Option(name = "-p", aliases = { "--price"}, description = "The item price", required = true, multiValued = false)
    private Float price;

    @Option(name = "-cat", aliases = { "--category" }, description = "The Category this item belongs to", required = true, multiValued = false)
    private String category;
    
    @Argument(name = "name", description = "Name of Category", required = true, multiValued = false)
    private String name;

	private InventoryEntityBroker broker;
	
	public void setBroker(InventoryEntityBroker broker) {
		this.broker = broker;
	}
    
    protected Object doExecute() throws Exception {
    	
    	InventoryCategory inventoryCategory = broker.getCategoryByName(category);
    	
		broker.addItem(name, description, price, inventoryCategory.getId());
    	
    	return null;
    }
}
