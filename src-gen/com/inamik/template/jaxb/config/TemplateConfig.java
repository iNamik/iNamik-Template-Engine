//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.08.16 at 12:58:07 AM PDT 
//


package com.inamik.template.jaxb.config;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


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
 *         &lt;element name="cache" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" default="default" />
 *                 &lt;attribute name="diskExpiryThreadIntervalSeconds" type="{http://www.w3.org/2001/XMLSchema}integer" default="120" />
 *                 &lt;attribute name="diskPersistent" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *                 &lt;attribute name="diskRoot" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="eternal" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *                 &lt;attribute name="maxElementsInMemory" type="{http://www.w3.org/2001/XMLSchema}integer" default="100" />
 *                 &lt;attribute name="memoryStoreEvictionPolicy" default="LFU">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *                       &lt;enumeration value="LRU"/>
 *                       &lt;enumeration value="LFU"/>
 *                       &lt;enumeration value="FIFO"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="overflowToDisk" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *                 &lt;attribute name="timeToIdleSeconds" type="{http://www.w3.org/2001/XMLSchema}integer" default="30" />
 *                 &lt;attribute name="timeToLiveSeconds" type="{http://www.w3.org/2001/XMLSchema}integer" default="300" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="use-lib" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="file" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="resource" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="prefix" type="{http://www.w3.org/2001/XMLSchema}string" default="main" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="applicationRoot" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="templateRoot" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="useDefaultLib" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="debug" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="tagDelimeter" default="CURLY">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="CURLY"/>
 *             &lt;enumeration value="CURLY_CURLY"/>
 *             &lt;enumeration value="LESS_THAN_PERCENT"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cache",
    "useLib"
})
@XmlRootElement(name = "template-config")
public class TemplateConfig {

    protected TemplateConfig.Cache cache;
    @XmlElement(name = "use-lib")
    protected List<TemplateConfig.UseLib> useLib;
    @XmlAttribute(name = "applicationRoot")
    protected String applicationRoot;
    @XmlAttribute(name = "templateRoot", required = true)
    protected String templateRoot;
    @XmlAttribute(name = "useDefaultLib")
    protected Boolean useDefaultLib;
    @XmlAttribute(name = "debug")
    protected Boolean debug;
    @XmlAttribute(name = "tagDelimeter")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String tagDelimeter;

    /**
     * Gets the value of the cache property.
     * 
     * @return
     *     possible object is
     *     {@link TemplateConfig.Cache }
     *     
     */
    public TemplateConfig.Cache getCache() {
        return cache;
    }

    /**
     * Sets the value of the cache property.
     * 
     * @param value
     *     allowed object is
     *     {@link TemplateConfig.Cache }
     *     
     */
    public void setCache(TemplateConfig.Cache value) {
        this.cache = value;
    }

    /**
     * Gets the value of the useLib property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the useLib property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUseLib().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TemplateConfig.UseLib }
     * 
     * 
     */
    public List<TemplateConfig.UseLib> getUseLib() {
        if (useLib == null) {
            useLib = new ArrayList<TemplateConfig.UseLib>();
        }
        return this.useLib;
    }

    /**
     * Gets the value of the applicationRoot property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationRoot() {
        return applicationRoot;
    }

    /**
     * Sets the value of the applicationRoot property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationRoot(String value) {
        this.applicationRoot = value;
    }

    /**
     * Gets the value of the templateRoot property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemplateRoot() {
        return templateRoot;
    }

    /**
     * Sets the value of the templateRoot property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemplateRoot(String value) {
        this.templateRoot = value;
    }

    /**
     * Gets the value of the useDefaultLib property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isUseDefaultLib() {
        if (useDefaultLib == null) {
            return true;
        } else {
            return useDefaultLib;
        }
    }

    /**
     * Sets the value of the useDefaultLib property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUseDefaultLib(Boolean value) {
        this.useDefaultLib = value;
    }

    /**
     * Gets the value of the debug property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isDebug() {
        if (debug == null) {
            return false;
        } else {
            return debug;
        }
    }

    /**
     * Sets the value of the debug property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDebug(Boolean value) {
        this.debug = value;
    }

    /**
     * Gets the value of the tagDelimeter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTagDelimeter() {
        if (tagDelimeter == null) {
            return "CURLY";
        } else {
            return tagDelimeter;
        }
    }

    /**
     * Sets the value of the tagDelimeter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTagDelimeter(String value) {
        this.tagDelimeter = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" default="default" />
     *       &lt;attribute name="diskExpiryThreadIntervalSeconds" type="{http://www.w3.org/2001/XMLSchema}integer" default="120" />
     *       &lt;attribute name="diskPersistent" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
     *       &lt;attribute name="diskRoot" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="eternal" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
     *       &lt;attribute name="maxElementsInMemory" type="{http://www.w3.org/2001/XMLSchema}integer" default="100" />
     *       &lt;attribute name="memoryStoreEvictionPolicy" default="LFU">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
     *             &lt;enumeration value="LRU"/>
     *             &lt;enumeration value="LFU"/>
     *             &lt;enumeration value="FIFO"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *       &lt;attribute name="overflowToDisk" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
     *       &lt;attribute name="timeToIdleSeconds" type="{http://www.w3.org/2001/XMLSchema}integer" default="30" />
     *       &lt;attribute name="timeToLiveSeconds" type="{http://www.w3.org/2001/XMLSchema}integer" default="300" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Cache {

        @XmlAttribute(name = "name")
        protected String name;
        @XmlAttribute(name = "diskExpiryThreadIntervalSeconds")
        protected BigInteger diskExpiryThreadIntervalSeconds;
        @XmlAttribute(name = "diskPersistent")
        protected Boolean diskPersistent;
        @XmlAttribute(name = "diskRoot")
        protected String diskRoot;
        @XmlAttribute(name = "eternal")
        protected Boolean eternal;
        @XmlAttribute(name = "maxElementsInMemory")
        protected BigInteger maxElementsInMemory;
        @XmlAttribute(name = "memoryStoreEvictionPolicy")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String memoryStoreEvictionPolicy;
        @XmlAttribute(name = "overflowToDisk")
        protected Boolean overflowToDisk;
        @XmlAttribute(name = "timeToIdleSeconds")
        protected BigInteger timeToIdleSeconds;
        @XmlAttribute(name = "timeToLiveSeconds")
        protected BigInteger timeToLiveSeconds;

        /**
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            if (name == null) {
                return "default";
            } else {
                return name;
            }
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * Gets the value of the diskExpiryThreadIntervalSeconds property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getDiskExpiryThreadIntervalSeconds() {
            if (diskExpiryThreadIntervalSeconds == null) {
                return new BigInteger("120");
            } else {
                return diskExpiryThreadIntervalSeconds;
            }
        }

        /**
         * Sets the value of the diskExpiryThreadIntervalSeconds property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setDiskExpiryThreadIntervalSeconds(BigInteger value) {
            this.diskExpiryThreadIntervalSeconds = value;
        }

        /**
         * Gets the value of the diskPersistent property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isDiskPersistent() {
            if (diskPersistent == null) {
                return false;
            } else {
                return diskPersistent;
            }
        }

        /**
         * Sets the value of the diskPersistent property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setDiskPersistent(Boolean value) {
            this.diskPersistent = value;
        }

        /**
         * Gets the value of the diskRoot property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDiskRoot() {
            return diskRoot;
        }

        /**
         * Sets the value of the diskRoot property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDiskRoot(String value) {
            this.diskRoot = value;
        }

        /**
         * Gets the value of the eternal property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isEternal() {
            if (eternal == null) {
                return true;
            } else {
                return eternal;
            }
        }

        /**
         * Sets the value of the eternal property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setEternal(Boolean value) {
            this.eternal = value;
        }

        /**
         * Gets the value of the maxElementsInMemory property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getMaxElementsInMemory() {
            if (maxElementsInMemory == null) {
                return new BigInteger("100");
            } else {
                return maxElementsInMemory;
            }
        }

        /**
         * Sets the value of the maxElementsInMemory property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setMaxElementsInMemory(BigInteger value) {
            this.maxElementsInMemory = value;
        }

        /**
         * Gets the value of the memoryStoreEvictionPolicy property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMemoryStoreEvictionPolicy() {
            if (memoryStoreEvictionPolicy == null) {
                return "LFU";
            } else {
                return memoryStoreEvictionPolicy;
            }
        }

        /**
         * Sets the value of the memoryStoreEvictionPolicy property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMemoryStoreEvictionPolicy(String value) {
            this.memoryStoreEvictionPolicy = value;
        }

        /**
         * Gets the value of the overflowToDisk property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isOverflowToDisk() {
            if (overflowToDisk == null) {
                return false;
            } else {
                return overflowToDisk;
            }
        }

        /**
         * Sets the value of the overflowToDisk property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setOverflowToDisk(Boolean value) {
            this.overflowToDisk = value;
        }

        /**
         * Gets the value of the timeToIdleSeconds property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getTimeToIdleSeconds() {
            if (timeToIdleSeconds == null) {
                return new BigInteger("30");
            } else {
                return timeToIdleSeconds;
            }
        }

        /**
         * Sets the value of the timeToIdleSeconds property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setTimeToIdleSeconds(BigInteger value) {
            this.timeToIdleSeconds = value;
        }

        /**
         * Gets the value of the timeToLiveSeconds property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getTimeToLiveSeconds() {
            if (timeToLiveSeconds == null) {
                return new BigInteger("300");
            } else {
                return timeToLiveSeconds;
            }
        }

        /**
         * Sets the value of the timeToLiveSeconds property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setTimeToLiveSeconds(BigInteger value) {
            this.timeToLiveSeconds = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="file" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="resource" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="prefix" type="{http://www.w3.org/2001/XMLSchema}string" default="main" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class UseLib {

        @XmlAttribute(name = "file")
        protected String file;
        @XmlAttribute(name = "resource")
        protected String resource;
        @XmlAttribute(name = "prefix")
        protected String prefix;

        /**
         * Gets the value of the file property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFile() {
            return file;
        }

        /**
         * Sets the value of the file property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFile(String value) {
            this.file = value;
        }

        /**
         * Gets the value of the resource property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getResource() {
            return resource;
        }

        /**
         * Sets the value of the resource property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setResource(String value) {
            this.resource = value;
        }

        /**
         * Gets the value of the prefix property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPrefix() {
            if (prefix == null) {
                return "main";
            } else {
                return prefix;
            }
        }

        /**
         * Sets the value of the prefix property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPrefix(String value) {
            this.prefix = value;
        }

    }

}
