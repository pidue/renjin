package org.renjin.gcc.codegen.var;

import com.google.common.base.Optional;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.renjin.gcc.codegen.WrapperType;
import org.renjin.gcc.codegen.expr.AbstractExprGenerator;
import org.renjin.gcc.codegen.expr.ExprGenerator;
import org.renjin.gcc.codegen.pointers.DereferencedPrimitivePtr;
import org.renjin.gcc.codegen.pointers.PrimitivePtrPtrPlus;
import org.renjin.gcc.gimple.type.GimpleIndirectType;
import org.renjin.gcc.gimple.type.GimpleType;
import org.renjin.gcc.runtime.ObjectPtr;


/**
 * Pointer to a pointer to a primitive, for example {@code double**}
 * 
 * <p>Given a Gimple local variable of type {@code double**}, we reserve local variable slots
 * for</p>
 * <pre>
 *   DoublePtr[] x;
 *   int x$offset = 0;
 * </pre>
 */
public class PrimitivePtrPtrVarGenerator extends AbstractExprGenerator implements VarGenerator {

  private final GimpleIndirectType pointerType;
  private final int arrayVarIndex;
  private final int offsetVarIndex;

  public PrimitivePtrPtrVarGenerator(GimpleIndirectType pointerType, int arrayVarIndex, int offsetVarIndex) {
    this.pointerType = pointerType;
    this.arrayVarIndex = arrayVarIndex;
    this.offsetVarIndex = offsetVarIndex;
  }

  @Override
  public void emitDefaultInit(MethodVisitor mv, Optional<ExprGenerator> initialValue) {
    if (initialValue.isPresent()) {
      emitStore(mv, initialValue.get());
    }
  }

  @Override
  public GimpleType getGimpleType() {
    return pointerType;
  }

  @Override
  public WrapperType getPointerType() {
    return WrapperType.valueOf(ObjectPtr.class);
  }

  @Override
  public void emitStore(MethodVisitor mv, ExprGenerator valueGenerator) {
    valueGenerator.emitPushPtrArrayAndOffset(mv);
    mv.visitVarInsn(Opcodes.ISTORE, offsetVarIndex);
    mv.visitVarInsn(Opcodes.ASTORE, arrayVarIndex);
  }

  @Override
  public void emitPushPointerWrapper(MethodVisitor mv) {
    super.emitPushPointerWrapper(mv);
  }

  @Override
  public void emitPushPtrArray(MethodVisitor mv) {
    mv.visitVarInsn(Opcodes.ALOAD, arrayVarIndex);
  }

  @Override
  public void emitPushPtrArrayAndOffset(MethodVisitor mv) {
    mv.visitVarInsn(Opcodes.ALOAD, arrayVarIndex);
    mv.visitVarInsn(Opcodes.ILOAD, offsetVarIndex);
  }

  @Override
  public ExprGenerator valueOf() {
    return new DereferencedPrimitivePtr(this);
  }


  @Override
  public ExprGenerator pointerPlus(ExprGenerator offsetInBytes) {
    return new PrimitivePtrPtrPlus(this, offsetInBytes);
  }
}
