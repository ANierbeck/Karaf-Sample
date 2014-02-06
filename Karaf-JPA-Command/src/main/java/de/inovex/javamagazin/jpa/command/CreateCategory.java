
package de.inovex.javamagazin.jpa.command;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.commands.Option;
import org.apache.karaf.shell.console.OsgiCommandSupport;

import de.inovex.javamagazin.jpa.broker.InventoryEntityBroker;

@Command(scope = "javamagazin", name = "CreateCategory", description = "CRUD commands for the JavaMagazin samples")
public class CreateCategory extends OsgiCommandSupport {

    @Option(name = "-d", aliases = { "--description" }, description = "A description to the category", required = false, multiValued = false)
    private String description;

    @Argument(name = "name", description = "Name of Category", required = true, multiValued = false)
    private String name;

	private InventoryEntityBroker broker;
	
	public void setBroker(InventoryEntityBroker broker) {
		this.broker = broker;
	}
    
    protected Object doExecute() throws Exception {
    	
    	broker.addCategory(name, description);
    	
    	return null;
    }
}
