
/* First created by JCasGen Thu Jan 23 08:13:05 CET 2014 */
package my.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Thu Jan 23 08:13:11 CET 2014
 * @generated */
public class Message_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Message_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Message_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Message(addr, Message_Type.this);
  			   Message_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Message(addr, Message_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Message.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("my.types.Message");
 
  /** @generated */
  final Feature casFeat_Body;
  /** @generated */
  final int     casFeatCode_Body;
  /** @generated */ 
  public String getBody(int addr) {
        if (featOkTst && casFeat_Body == null)
      jcas.throwFeatMissing("Body", "my.types.Message");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Body);
  }
  /** @generated */    
  public void setBody(int addr, String v) {
        if (featOkTst && casFeat_Body == null)
      jcas.throwFeatMissing("Body", "my.types.Message");
    ll_cas.ll_setStringValue(addr, casFeatCode_Body, v);}
    
  
 
  /** @generated */
  final Feature casFeat_Id;
  /** @generated */
  final int     casFeatCode_Id;
  /** @generated */ 
  public String getId(int addr) {
        if (featOkTst && casFeat_Id == null)
      jcas.throwFeatMissing("Id", "my.types.Message");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Id);
  }
  /** @generated */    
  public void setId(int addr, String v) {
        if (featOkTst && casFeat_Id == null)
      jcas.throwFeatMissing("Id", "my.types.Message");
    ll_cas.ll_setStringValue(addr, casFeatCode_Id, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Message_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_Body = jcas.getRequiredFeatureDE(casType, "Body", "uima.cas.String", featOkTst);
    casFeatCode_Body  = (null == casFeat_Body) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Body).getCode();

 
    casFeat_Id = jcas.getRequiredFeatureDE(casType, "Id", "uima.cas.String", featOkTst);
    casFeatCode_Id  = (null == casFeat_Id) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Id).getCode();

  }
}



    