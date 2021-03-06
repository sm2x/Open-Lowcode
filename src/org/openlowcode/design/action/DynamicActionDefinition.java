/********************************************************************************
 * Copyright (c) 2019-2020 [Open Lowcode SAS](https://openlowcode.com/)
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0 .
 *
 * SPDX-License-Identifier: EPL-2.0
 ********************************************************************************/

package org.openlowcode.design.action;

import org.openlowcode.design.data.ArgumentContent;
import org.openlowcode.design.data.DataObjectDefinition;
import org.openlowcode.design.data.argument.ObjectArgument;
import org.openlowcode.design.data.argument.ObjectIdArgument;
import org.openlowcode.tools.misc.NamedList;

/**
 * A dynamic action is an action that has input attributes. Dynamic actions are
 * not suited to menu items of the module
 * 
 * @author <a href="https://openlowcode.com/" rel="nofollow">Open Lowcode
 *         SAS</a>
 *
 */
public class DynamicActionDefinition
		extends
		ActionDefinition {
	private NamedList<ArgumentContent> inputarguments;

	private ObjectArgument securityobjectargument = null;
	private ObjectIdArgument securityobjectidargument = null;

	/**
	 * creates a dynamic action definition that is not automatically generated
	 * 
	 * @param name name of the action (that is unique for the module)
	 */
	public DynamicActionDefinition(String name) {
		super(name);
		inputarguments = new NamedList<ArgumentContent>();

	}

	/**
	 * creates a dynamic action definition
	 * 
	 * @param name      name of the action (that is unique for the module)
	 * @param generated true if the action is generated (an action should be set to
	 *                  true only if it is part of the standard actions for objects
	 *                  generated by the package
	 */
	public DynamicActionDefinition(String name, boolean generated) {
		super(name, generated);
		inputarguments = new NamedList<ArgumentContent>();

	}

	/**
	 * creates a dynamic action definition with a written specification (that will
	 * be put in the javadoc of the auto-generated action
	 * 
	 * @param name          name of the action (that is unique for the module)
	 * @param specification specification of the action to be put in javadoc
	 */
	public DynamicActionDefinition(String name, String specification) {
		super(name, specification);
		inputarguments = new NamedList<ArgumentContent>();

	}

	/**
	 * adds an input argument to the action (without usage for security checks)
	 * 
	 * @param thisargument the argument to add
	 */
	public void addInputArgument(ArgumentContent thisargument) {
		inputarguments.add(thisargument);
	}

	/**
	 * adds an input argument that will be used for access criteria (security
	 * check). Typically, this is an object or an object id. There can be only one
	 * access criteria argument amongst input arguments
	 * 
	 * @param thisargument argument to add as security criteria.
	 */
	public void addInputArgumentAsAccessCriteria(ArgumentContent thisargument) {
		accesscriteriaindex = inputarguments.getSize();
		inputarguments.add(thisargument);
		if (accesscriteria != null)
			throw new RuntimeException("there cannot be two access criteria for ActionDefinition " + this.getName());
		accesscriteriaisinput = true;
		accesscriteria = thisargument;
		if (thisargument.getMasterObject() == null)
			throw new RuntimeException("An access criteria argument should have a master object, action "
					+ this.getName() + " for argument " + thisargument.getName());
		DataObjectDefinition thisobject = thisargument.getMasterObject();
		thisobject.addActionToFullGroup(this);

	}

	@Override
	public NamedList<ArgumentContent> getInputArguments() {
		return inputarguments;
	}

	@Override
	public ObjectArgument getSecurityobjectargument() {

		return securityobjectargument;
	}

	@Override
	public ObjectIdArgument getSecurityobjectidargument() {

		return securityobjectidargument;
	}

}
