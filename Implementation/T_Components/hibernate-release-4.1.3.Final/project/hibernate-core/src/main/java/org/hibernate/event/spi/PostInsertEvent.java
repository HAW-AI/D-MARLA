/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2008-2011, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.event.spi;

import java.io.Serializable;

import org.hibernate.persister.entity.EntityPersister;

/**
 * Occurs after inserting an item in the datastore
 * 
 * @author Gavin King
 */
public class PostInsertEvent extends AbstractEvent {
	private Object entity;
	private EntityPersister persister;
	private Object[] state;
	private Serializable id;
	
	public PostInsertEvent(
			Object entity, 
			Serializable id,
			Object[] state,
			EntityPersister persister,
			EventSource source
	) {
		super(source);
		this.entity = entity;
		this.id = id;
		this.state = state;
		this.persister = persister;
	}
	
	public Object getEntity() {
		return entity;
	}
	public Serializable getId() {
		return id;
	}
	public EntityPersister getPersister() {
		return persister;
	}
	public Object[] getState() {
		return state;
	}
}
