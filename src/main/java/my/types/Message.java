

/* First created by JCasGen Thu Jan 23 08:13:05 CET 2014 */
package my.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Thu Jan 23 08:13:11 CET 2014
 * XML source: /home/agathe/ATAL/nlp-software-development/workspace/atal2project/src/main/resources/my/types/MailTS.xml
 * @generated */
public class Message extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Message.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Message() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Message(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Message(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Message(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: Body

  /** getter for Body - gets 
   * @generated */
  public String getBody() {
    if (Message_Type.featOkTst && ((Message_Type)jcasType).casFeat_Body == null)
      jcasType.jcas.throwFeatMissing("Body", "my.types.Message");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Message_Type)jcasType).casFeatCode_Body);}
    
  /** setter for Body - sets  
   * @generated */
  public void setBody(String v) {
    if (Message_Type.featOkTst && ((Message_Type)jcasType).casFeat_Body == null)
      jcasType.jcas.throwFeatMissing("Body", "my.types.Message");
    jcasType.ll_cas.ll_setStringValue(addr, ((Message_Type)jcasType).casFeatCode_Body, v);}    
   
    
  //*--------------*
  //* Feature: Id

  /** getter for Id - gets 
   * @generated */
  public String getId() {
    if (Message_Type.featOkTst && ((Message_Type)jcasType).casFeat_Id == null)
      jcasType.jcas.throwFeatMissing("Id", "my.types.Message");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Message_Type)jcasType).casFeatCode_Id);}
    
  /** setter for Id - sets  
   * @generated */
  public void setId(String v) {
    if (Message_Type.featOkTst && ((Message_Type)jcasType).casFeat_Id == null)
      jcasType.jcas.throwFeatMissing("Id", "my.types.Message");
    jcasType.ll_cas.ll_setStringValue(addr, ((Message_Type)jcasType).casFeatCode_Id, v);}    
  }

    