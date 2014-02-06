// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.package tutorial;
package de.inovex.javamagazin.jpa.broker.impl;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import de.inovex.javamagazin.jpa.InventoryCategory;
import de.inovex.javamagazin.jpa.InventoryItem;
import de.inovex.javamagazin.jpa.broker.InventoryEntityBroker;

public class InventoryEntityBrokerImpl implements InventoryEntityBroker {
    EntityManager em;

    void close() {
        em.close();
    }
    
	/**
	 * @param em
	 *            the em to set
	 */
	public void setEm(EntityManager em) {
		this.em = em;
	}

    // Item methods
    /* (non-Javadoc)
	 * @see de.inovex.javamagazin.jpa.broker.impl.InventoryEntityBroker#getAllItems()
	 */
    public List<InventoryItem> getAllItems() {
        Query q = em.createQuery("SELECT item FROM InventoryItem item ORDER BY item.itemName");

        return (List<InventoryItem>) q.getResultList();
    }

    /* (non-Javadoc)
	 * @see de.inovex.javamagazin.jpa.broker.impl.InventoryEntityBroker#getSingleItem(int)
	 */
    public InventoryItem getSingleItem(int id) {
        Query q = em.createQuery("SELECT item FROM InventoryItem item WHERE item.id=" + id);
        return (InventoryItem) q.getSingleResult();
    }
    
    /* (non-Javadoc)
	 * @see de.inovex.javamagazin.jpa.broker.impl.InventoryEntityBroker#addItem(java.lang.String, java.lang.String, float, int)
	 */
    public void addItem(String name, String description, float price, int categoryID) 
    {

        InventoryItem item = new InventoryItem();
        item.setItemName(name);
        item.setItemDescription(description);
        item.setItemPrice(price);
        item.setCategory(getSingleCategory(categoryID));
        
        em.persist(item);
    }

    /* (non-Javadoc)
	 * @see de.inovex.javamagazin.jpa.broker.impl.InventoryEntityBroker#updateItem(int, java.lang.String, java.lang.String, float, int)
	 */
    public void updateItem(int id, String name, String description, float price, int categoryID) {

    	InventoryItem item = em.find(InventoryItem.class, id);
        item.setItemName(name);
        item.setItemDescription(description);
        item.setItemPrice(price);
        item.setCategory(getSingleCategory(categoryID));

        em.merge(item);
    }

    /* (non-Javadoc)
	 * @see de.inovex.javamagazin.jpa.broker.impl.InventoryEntityBroker#deleteItem(int)
	 */
    public void deleteItem(int id) {
    	InventoryItem item = em.find(InventoryItem.class, id);

        em.remove(item);
    }
    
//Category Methods    
    /* (non-Javadoc)
	 * @see de.inovex.javamagazin.jpa.broker.impl.InventoryEntityBroker#getAllCategories()
	 */
    public List<InventoryCategory> getAllCategories() {
        Query q = em.createQuery("SELECT cat FROM InventoryCategory cat ORDER BY cat.categoryName");

        return (List<InventoryCategory>) q.getResultList();
    }

    /* (non-Javadoc)
	 * @see de.inovex.javamagazin.jpa.broker.impl.InventoryEntityBroker#getSingleCategory(int)
	 */
    public InventoryCategory getSingleCategory(int id) {
        Query q = em.createQuery("SELECT cat FROM InventoryCategory cat WHERE cat.id=" + id);
        return (InventoryCategory) q.getSingleResult();
    }
    
    /* (non-Javadoc)
	 * @see de.inovex.javamagazin.jpa.broker.impl.InventoryEntityBroker#addCategory(java.lang.String, java.lang.String)
	 */
    public void addCategory(String name, String description) 
    {
        InventoryCategory cat = new InventoryCategory();
        cat.setCategoryName(name);
        cat.setCategoryDescription(description);
        
        em.persist(cat);
    }

    /* (non-Javadoc)
	 * @see de.inovex.javamagazin.jpa.broker.impl.InventoryEntityBroker#updateCategory(int, java.lang.String, java.lang.String)
	 */
    public void updateCategory(int id, String name, String description) {

    	InventoryCategory cat = em.find(InventoryCategory.class, id);
        cat.setCategoryName(name);
        cat.setCategoryDescription(description);

        em.merge(cat);
    }

    /* (non-Javadoc)
	 * @see de.inovex.javamagazin.jpa.broker.impl.InventoryEntityBroker#deleteCategory(int)
	 */
    public void deleteCategory(int id) {
    	InventoryCategory cat = em.find(InventoryCategory.class, id);

        em.remove(cat);
    }

	public void deleteCategoryByName(String name) {
		em.remove(getCategoryByName(name));
	}

	public InventoryCategory getCategoryByName(String name) {
		TypedQuery<InventoryCategory> query = em.createQuery("SELECT cat FROM InventoryCategory cat where cat.categoryName = :name", InventoryCategory.class);
		query.setParameter("name", name);
		
		return query.getSingleResult();
	}

	public List<InventoryItem> getAllItemsByCategory(String categoryName) {
		TypedQuery<InventoryItem> query = em.createQuery("SELECT item FROM InventoryItem item where item.category.categoryName = :name ORDER BY item.itemName", InventoryItem.class);
		query.setParameter("name", categoryName);
        return query.getResultList();
	}

	public void deleteItemByName(String name) {
		InventoryItem inventoryItem = getInventoryItemByName(name); 
		deleteItem(inventoryItem.getId());
	}

	public InventoryItem getInventoryItemByName(String name) {
		TypedQuery<InventoryItem> query = em.createQuery("SELECT item FROM InventoryItem item where item.itemName = :name", InventoryItem.class);
		query.setParameter("name", name);
		return query.getSingleResult();
	}
}
