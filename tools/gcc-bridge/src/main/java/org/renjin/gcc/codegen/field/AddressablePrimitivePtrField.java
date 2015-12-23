package org.renjin.gcc.codegen.field;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.renjin.gcc.codegen.WrapperType;
import org.renjin.gcc.codegen.expr.AbstractExprGenerator;
import org.renjin.gcc.codegen.expr.ExprGenerator;
import org.renjin.gcc.codegen.pointers.DereferencedPrimitivePtr;
import org.renjin.gcc.gimple.type.GimpleIndirectType;
import org.renjin.gcc.gimple.type.GimplePointerType;
import org.renjin.gcc.gimple.type.GimpleType;

/**
 * Pointer to a primitive field whose address can be read.
 */
public class AddressablePrimitivePtrField extends FieldGenerator {

  private String className;
  private String fieldName;
  private GimpleIndirectType pointerType;
  private final WrapperType wrapperType;
  private final String fieldDescriptor;


  public AddressablePrimitivePtrField(String className, String fieldName, GimpleIndirectType pointerType) {
    this.className = className;
    this.fieldName = fieldName;
    this.pointerType = pointerType;
    this.wrapperType = WrapperType.forPointerType(pointerType);
    this.fieldDescriptor = "[" + wrapperType.getWrapperType().getDescriptor();
  }

  @Override
  public GimpleType getType() {
    return pointerType;
  }

  @Override
  public void emitInstanceField(ClassVisitor cv) {
    cv.visitField(Opcodes.ACC_PUBLIC, fieldName, fieldDescriptor, null, null).visitEnd();
  }
  
  @Override
  public void emitInstanceInit(MethodVisitor mv) {
    mv.visitVarInsn(Opcodes.ALOAD, 0); // this
    mv.visitInsn(Opcodes.ICONST_1);
    mv.visitTypeInsn(Opcodes.ANEWARRAY, wrapperType.getWrapperType().getInternalName());
    mv.visitFieldInsn(Opcodes.PUTFIELD, className, fieldName, fieldDescriptor);
  }

  @Override
  public ExprGenerator memberExprGenerator(ExprGenerator instanceGenerator) {
    return new DereferencedPrimitivePtr(new MemberAddressExpr(instanceGenerator));
  }

  private class MemberAddressExpr extends AbstractExprGenerator {
    private ExprGenerator instanceGenerator;

    public MemberAddressExpr(ExprGenerator instanceGenerator) {
      this.instanceGenerator = instanceGenerator;
    }


    @Override
    public GimpleType getGimpleType() {
      return new GimplePointerType(pointerType);
    }

    @Override
    public void emitPushPtrArrayAndOffset(MethodVisitor mv) {
      instanceGenerator.emitPushRecordRef(mv);
      mv.visitFieldInsn(Opcodes.GETFIELD, className, fieldName, fieldDescriptor);
      mv.visitInsn(Opcodes.ICONST_0);
    }
  }
}
