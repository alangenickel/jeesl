//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.2-hudson-jaxb-ri-2.2-63- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.12.07 at 09:26:58 AM MEZ 
//


package net.sf.ahtutils.xml.access;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://ahtutils.aht-group.com/access}usecaseCategory"/>
 *         &lt;element ref="{http://ahtutils.aht-group.com/access}projectRoleCategory"/>
 *       &lt;/sequence>
 *       &lt;attribute name="lang" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="delQueryAfterProcessing" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "usecaseCategory",
    "projectRoleCategory"
})
@XmlRootElement(name = "aclQuery")
public class AclQuery
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected UsecaseCategory usecaseCategory;
    @XmlElement(required = true)
    protected ProjectRoleCategory projectRoleCategory;
    @XmlAttribute(name = "lang")
    protected String lang;
    @XmlAttribute(name = "delQueryAfterProcessing")
    protected Boolean delQueryAfterProcessing;

    /**
     * Gets the value of the usecaseCategory property.
     * 
     * @return
     *     possible object is
     *     {@link UsecaseCategory }
     *     
     */
    public UsecaseCategory getUsecaseCategory() {
        return usecaseCategory;
    }

    /**
     * Sets the value of the usecaseCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link UsecaseCategory }
     *     
     */
    public void setUsecaseCategory(UsecaseCategory value) {
        this.usecaseCategory = value;
    }

    public boolean isSetUsecaseCategory() {
        return (this.usecaseCategory!= null);
    }

    /**
     * Gets the value of the projectRoleCategory property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectRoleCategory }
     *     
     */
    public ProjectRoleCategory getProjectRoleCategory() {
        return projectRoleCategory;
    }

    /**
     * Sets the value of the projectRoleCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectRoleCategory }
     *     
     */
    public void setProjectRoleCategory(ProjectRoleCategory value) {
        this.projectRoleCategory = value;
    }

    public boolean isSetProjectRoleCategory() {
        return (this.projectRoleCategory!= null);
    }

    /**
     * Gets the value of the lang property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the value of the lang property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLang(String value) {
        this.lang = value;
    }

    public boolean isSetLang() {
        return (this.lang!= null);
    }

    /**
     * Gets the value of the delQueryAfterProcessing property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isDelQueryAfterProcessing() {
        return delQueryAfterProcessing;
    }

    /**
     * Sets the value of the delQueryAfterProcessing property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDelQueryAfterProcessing(boolean value) {
        this.delQueryAfterProcessing = value;
    }

    public boolean isSetDelQueryAfterProcessing() {
        return (this.delQueryAfterProcessing!= null);
    }

    public void unsetDelQueryAfterProcessing() {
        this.delQueryAfterProcessing = null;
    }

}
