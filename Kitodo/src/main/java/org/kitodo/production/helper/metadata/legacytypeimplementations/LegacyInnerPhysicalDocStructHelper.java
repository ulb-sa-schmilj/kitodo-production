/*
 * (c) Kitodo. Key to digital objects e. V. <contact@kitodo.org>
 *
 * This file is part of the Kitodo project.
 *
 * It is licensed under GNU General Public License version 3 or later.
 *
 * For the full copyright and license information, please read the
 * GPL3-License.txt file that was distributed with this source code.
 */

package org.kitodo.production.helper.metadata.legacytypeimplementations;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kitodo.api.dataformat.mets.FLocatXmlElementAccessInterface;
import org.kitodo.api.dataformat.mets.FileXmlElementAccessInterface;
import org.kitodo.api.dataformat.mets.UseXmlAttributeAccessInterface;
import org.kitodo.api.ugh.ContentFileInterface;
import org.kitodo.api.ugh.DigitalDocumentInterface;
import org.kitodo.api.ugh.DocStructInterface;
import org.kitodo.api.ugh.DocStructTypeInterface;
import org.kitodo.api.ugh.MetadataGroupInterface;
import org.kitodo.api.ugh.MetadataGroupTypeInterface;
import org.kitodo.api.ugh.MetadataInterface;
import org.kitodo.api.ugh.MetadataTypeInterface;
import org.kitodo.api.ugh.PersonInterface;
import org.kitodo.api.ugh.PrefsInterface;
import org.kitodo.api.ugh.ReferenceInterface;
import org.kitodo.api.ugh.exceptions.ContentFileNotLinkedException;
import org.kitodo.api.ugh.exceptions.MetadataTypeNotAllowedException;
import org.kitodo.api.ugh.exceptions.TypeNotAllowedAsChildException;
import org.kitodo.api.ugh.exceptions.TypeNotAllowedForParentException;
import org.kitodo.production.services.ServiceManager;
import org.kitodo.production.services.dataformat.MetsService;

/**
 * Connects a legacy doc struct from the physical map to a media unit. This is a
 * soldering class to keep legacy code operational which is about to be removed.
 * Do not use this class.
 */
public class LegacyInnerPhysicalDocStructHelper implements DocStructInterface {
    private static final Logger logger = LogManager.getLogger(LegacyInnerPhysicalDocStructHelper.class);

    private static final MetsService metsService = ServiceManager.getMetsService();

    /**
     * A constant for local use. We cannot make this constant constant because
     * the service loader is an instance variable.
     */
    private final UseXmlAttributeAccessInterface local = metsService.createUseXmlAttributeAccess();

    {
        local.setUse("LOCAL");
        local.setMimeType("image/tiff");
    }

    /**
     * The media unit accessed via this soldering class.
     */
    private FileXmlElementAccessInterface mediaUnit;

    public LegacyInnerPhysicalDocStructHelper() {
        this.mediaUnit = metsService.createFileXmlElementAccess();
    }

    public LegacyInnerPhysicalDocStructHelper(FileXmlElementAccessInterface mediaUnit) {
        this.mediaUnit = mediaUnit;
    }

    @Override
    public void addChild(DocStructInterface child) throws TypeNotAllowedAsChildException {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public void addChild(Integer index, DocStructInterface child) throws TypeNotAllowedAsChildException {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public void addContentFile(ContentFileInterface contentFile) {
        mediaUnit.putFLocatForUse(local, ((LegacyContentFileHelper) contentFile).getMediaFile());
    }

    @Override
    public void addMetadata(MetadataInterface metadata) throws MetadataTypeNotAllowedException {
        if (LegacyMetadataTypeHelper.SPECIAL_TYPE_ORDER.equals(metadata.getMetadataType())) {
            mediaUnit.setOrder(Integer.parseInt(metadata.getValue()));
        } else if (LegacyMetadataTypeHelper.SPECIAL_TYPE_ORDERLABEL.equals(metadata.getMetadataType())) {
            mediaUnit.setOrderlabel(metadata.getValue());
        } else {
            logger.log(Level.TRACE, "addMetadata(metadata: {})", metadata);
        }
    }

    @Override
    public DocStructInterface addMetadata(String metadataType, String value) throws MetadataTypeNotAllowedException {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public void addMetadataGroup(MetadataGroupInterface metadataGroup) throws MetadataTypeNotAllowedException {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public void addPerson(PersonInterface person) throws MetadataTypeNotAllowedException {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public ReferenceInterface addReferenceTo(DocStructInterface docStruct, String type) {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public DocStructInterface copy(boolean copyMetaData, Boolean recursive) {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public DocStructInterface createChild(String docStructType, DigitalDocumentInterface digitalDocument,
            PrefsInterface prefs) throws TypeNotAllowedAsChildException, TypeNotAllowedForParentException {

        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public void deleteUnusedPersonsAndMetadata() {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public List<MetadataGroupTypeInterface> getAddableMetadataGroupTypes() {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public List<MetadataTypeInterface> getAddableMetadataTypes() {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public List<DocStructInterface> getAllChildren() {
        /*
         * Although the method is called because the same loop is used for
         * logical and physical structure elements, it must come back empty.
         */
        return Collections.emptyList();
    }

    @Override
    public List<DocStructInterface> getAllChildrenByTypeAndMetadataType(String docStructType, String metaDataType) {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public List<ContentFileInterface> getAllContentFiles() {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public List<ReferenceInterface> getAllFromReferences() {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public List<MetadataInterface> getAllIdentifierMetadata() {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public List<MetadataInterface> getAllMetadata() {
        return Arrays.asList(
            new LegacyMetadataHelper(this, LegacyMetadataTypeHelper.SPECIAL_TYPE_ORDER,
                    Integer.toString(mediaUnit.getOrder())),
            new LegacyMetadataHelper(this, LegacyMetadataTypeHelper.SPECIAL_TYPE_ORDERLABEL,
                    mediaUnit.getOrderlabel()));
    }

    @Override
    public List<? extends MetadataInterface> getAllMetadataByType(MetadataTypeInterface metadataType) {
        if (metadataType == LegacyMetadataTypeHelper.SPECIAL_TYPE_ORDER) {
            return Arrays.asList(new LegacyMetadataHelper(this, metadataType, Integer.toString(mediaUnit.getOrder())));
        } else if (metadataType == LegacyMetadataTypeHelper.SPECIAL_TYPE_ORDERLABEL) {
            return Objects.nonNull(mediaUnit.getOrderlabel())
                    ? Arrays.asList(new LegacyMetadataHelper(this, metadataType, mediaUnit.getOrderlabel()))
                    : Collections.emptyList();
        } else {
            // TODO remove
            throw andLog(new UnsupportedOperationException("Not yet implemented"));
        }
    }

    @Override
    public List<MetadataGroupInterface> getAllMetadataGroups() {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public List<PersonInterface> getAllPersons() {
        /*
         * Although the method is called because the same loop is used for
         * logical and physical structure elements, it must come back empty.
         */
        return Collections.emptyList();
    }

    @Override
    public List<PersonInterface> getAllPersonsByType(MetadataTypeInterface metadataType) {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public List<ReferenceInterface> getAllReferences(String direction) {
        /*
         * Although the method is called because the same loop is used for
         * logical and physical structure elements, it must come back empty.
         */
        return Collections.emptyList();
    }

    @Override
    public Collection<ReferenceInterface> getAllToReferences() {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public Collection<ReferenceInterface> getAllToReferences(String type) {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public Object getAllVisibleMetadata() {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public String getAnchorClass() {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public DocStructInterface getChild(String type, String identifierField, String identifier) {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public List<MetadataTypeInterface> getDisplayMetadataTypes() {
        return Collections.emptyList();
    }

    @Override
    public String getImageName() {
        FLocatXmlElementAccessInterface uri = this.mediaUnit.getFLocatForUse(local);
        return new File(uri.getUri().getPath()).getName();
    }

    FileXmlElementAccessInterface getMediaUnit() {
        return mediaUnit;
    }

    @Override
    public DocStructInterface getNextChild(DocStructInterface predecessor) {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public DocStructInterface getParent() {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public List<MetadataTypeInterface> getPossibleMetadataTypes() {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public DocStructTypeInterface getDocStructType() {
        return LegacyInnerPhysicalDocStructTypePageHelper.INSTANCE;
    }

    /**
     * This method is not part of the interface, but the JSF code digs in the
     * depths of the UGH and uses it on the guts.
     * 
     * @return Method delegated to {@link #getDocStructType()}
     */
    public DocStructTypeInterface getType() {
        if (!logger.isTraceEnabled()) {
            StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
            logger.log(Level.WARN, "Method {}.{}() invokes {}.{}(), bypassing the interface!",
                stackTrace[1].getClassName(), stackTrace[1].getMethodName(), stackTrace[0].getClassName(),
                stackTrace[0].getMethodName());
        }
        return getDocStructType();
    }

    @Override
    public boolean isDocStructTypeAllowedAsChild(DocStructTypeInterface type) {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public void removeChild(DocStructInterface docStruct) {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public void removeContentFile(ContentFileInterface contentFile) throws ContentFileNotLinkedException {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public void removeMetadata(MetadataInterface metaDatum) {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public void removeMetadataGroup(MetadataGroupInterface metadataGroup) {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public void removePerson(PersonInterface person) {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public void removeReferenceTo(DocStructInterface target) {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));

    }

    @Override
    public void setImageName(String imageName) {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public void setType(DocStructTypeInterface docStructType) {
        //TODO remove
        throw andLog(new UnsupportedOperationException("Not yet implemented"));
    }

    /**
     * This method generates a comprehensible log message in case something was
     * overlooked and one of the unimplemented methods should ever be called in
     * operation. The name was chosen deliberately short in order to keep the
     * calling code clear. This method must be implemented in every class
     * because it uses the logger tailored to the class.
     * 
     * @param exception
     *            created {@code UnsupportedOperationException}
     * @return the exception
     */
    private static RuntimeException andLog(UnsupportedOperationException exception) {
        StackTraceElement[] stackTrace = exception.getStackTrace();
        StringBuilder buffer = new StringBuilder(255);
        buffer.append(stackTrace[1].getClassName());
        buffer.append('.');
        buffer.append(stackTrace[1].getMethodName());
        buffer.append("()");
        if (stackTrace[1].getLineNumber() > -1) {
            buffer.append(" line ");
            buffer.append(stackTrace[1].getLineNumber());
        }
        buffer.append(" unexpectedly called unimplemented ");
        buffer.append(stackTrace[0].getMethodName());
        buffer.append("()");
        if (exception.getMessage() != null) {
            buffer.append(": ");
            buffer.append(exception.getMessage());
        }
        logger.error(buffer.toString());
        return exception;
    }
}