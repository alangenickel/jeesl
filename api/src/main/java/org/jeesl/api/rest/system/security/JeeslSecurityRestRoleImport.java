package org.jeesl.api.rest.system.security;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.xml.security.Security;
import net.sf.ahtutils.xml.sync.DataUpdate;

public interface JeeslSecurityRestRoleImport
{
	@POST @Path("/admin/security/roles")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	DataUpdate iuSecurityRoles(Security roles);
}
