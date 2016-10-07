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
public class BinaryIntersection<B1 extends BooleanType<B1>, B2 extends BooleanType<B2>> extends AbstractBinaryROIOperator<B1, B2> implements Contains<RealLocalizable> {
    public BinaryIntersection(RandomAccessibleInterval leftOperand,
                                  RandomAccessibleInterval rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public boolean contains(RealLocalizable l) {
        RandomAccess<B1> leftRa = leftOperand.randomAccess();
        RandomAccess<B2> rightRa = rightOperand.randomAccess();
        long[] pos = new long[l.numDimensions()];
        for (int d = 0; d < l.numDimensions(); d++)
        {
            pos[d] = (long)l.getDoublePosition(d);
        }
        leftRa.setPosition(pos);
        rightRa.setPosition(pos);

        return leftRa.get().get() && rightRa.get().get();
    }


}
