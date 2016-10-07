package de.mpicbg.scf.imgtools.geometry.filter.operators;

import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.RealLocalizable;
import net.imglib2.roi.util.Contains;
import net.imglib2.type.BooleanType;

/**
 * Author: Robert Haase, Scientific Computing Facility, MPI-CBG Dresden, rhaase@mpi-cbg.de
 * Date: September 2016
 */
public class BinaryWrap<B extends BooleanType<B>> extends AbstractBinaryROIOperator<B, B> implements Contains<RealLocalizable> {
    public BinaryWrap(RandomAccessibleInterval operand) {
        super(operand, operand);
    }

    @Override
    public boolean contains(RealLocalizable l) {
        RandomAccess<B> operandRA = leftOperand.randomAccess();

        long[] pos = new long[l.numDimensions()];
        for (int d = 0; d < l.numDimensions(); d++)
        {
            pos[d] = (long)l.getDoublePosition(d);
        }
        operandRA.setPosition(pos);

        return operandRA.get().get();
    }
}
