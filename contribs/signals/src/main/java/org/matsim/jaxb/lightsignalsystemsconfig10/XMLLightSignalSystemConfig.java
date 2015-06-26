//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.09.20 at 07:20:42 PM MESZ 
//


package org.matsim.jaxb.lightsignalsystemsconfig10;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *          This is the root element for configuration of the traffic light system.
 *       
 * 
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="lightSignalSystemConfiguration" type="{http://www.matsim.org/files/dtd}lightSignalSystemConfigurationType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "lightSignalSystemConfiguration"
})
@XmlRootElement(name = "lightSignalSystemConfig")
public class XMLLightSignalSystemConfig {

    protected List<XMLLightSignalSystemConfigurationType> lightSignalSystemConfiguration;

    /**
     * Gets the value of the lightSignalSystemConfiguration property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lightSignalSystemConfiguration property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLightSignalSystemConfiguration().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link XMLLightSignalSystemConfigurationType }
     * 
     * 
     */
    public List<XMLLightSignalSystemConfigurationType> getLightSignalSystemConfiguration() {
        if (lightSignalSystemConfiguration == null) {
            lightSignalSystemConfiguration = new ArrayList<XMLLightSignalSystemConfigurationType>();
        }
        return this.lightSignalSystemConfiguration;
    }

}