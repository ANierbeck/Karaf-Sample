package de.inovex.javamagazin.jpa.command;

import java.util.List;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.commands.Option;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.apache.karaf.shell.table.Col;
import org.apache.karaf.shell.table.ShellTable;

import de.inovex.javamagazin.jpa.InventoryItem;
import de.inovex.javamagazin.jpa.broker.InventoryEntityBroker;

@Command(scope = "javamagazin", name = "ListItem", description = "CRUD commands for the JavaMagazin samples")
public class ListItem extends OsgiCommandSupport {

	@Option(name = "--no-format", description = "Disable table rendered output", required = false, multiValued = false)
	boolean noFormat;

	@Argument(name = "name", description = "Name of Category", required = false, multiValued = false)
	private String categoryName;

	private InventoryEntityBroker broker;

	public void setBroker(InventoryEntityBroker broker) {
		this.broker = broker;
	}

	protected Object doExecute() throws Exception {

		List<InventoryItem> allItems = null;
		if (categoryName == null) {
			allItems = broker.getAllItems();
		} else {
			allItems = broker.getAllItemsByCategory(categoryName);
		}

		ShellTable table = new ShellTable();
		table.column(new Col("ID"));
		table.column(new Col("Name"));
		table.column(new Col("Description"));
		table.column(new Col("Price"));
		table.column(new Col("Category"));

		for (InventoryItem item : allItems) {

			table.addRow().addContent(item.getId(), item.getItemName(),
					item.getItemDescription(), item.getItemPrice(),
					item.getCategory().getCategoryName());

		}

		table.print(System.out, !noFormat);

		return null;
	}
}
