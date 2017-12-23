package com.steammachine.org.junit5.extensions.ignore;

/**
 * Created 15/11/16 18:16
 *
 * @author Vladimir Bogodukhov 
 **/
public class ConditionWithProperties extends DefaultIgnoreCondition {

    private String str;
    private boolean bool;
    private Boolean boolO;
    private byte b;
    private Byte bo;
    private Short s;
    private Short so;
    private int i;
    private Integer io;
    private long l;
    private Long lo;
    private Integer nullableinteger = 2;

    public void setNullableinteger(Integer nullableinteger) {
        this.nullableinteger = nullableinteger;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public void setBoolO(Boolean boolO) {
        this.boolO = boolO;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public void setB(byte b) {
        this.b = b;
    }

    public void setBo(Byte bo) {
        this.bo = bo;
    }

    public void setS(Short s) {
        this.s = s;
    }

    public void setSo(Short so) {
        this.so = so;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setIo(Integer io) {
        this.io = io;
    }

    public void setL(long l) {
        this.l = l;
    }

    public void setLo(Long lo) {
        this.lo = lo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConditionWithProperties)) return false;

        ConditionWithProperties that = (ConditionWithProperties) o;

        if (bool != that.bool) return false;
        if (b != that.b) return false;
        if (i != that.i) return false;
        if (l != that.l) return false;
        if (str != null ? !str.equals(that.str) : that.str != null) return false;
        if (boolO != null ? !boolO.equals(that.boolO) : that.boolO != null) return false;
        if (bo != null ? !bo.equals(that.bo) : that.bo != null) return false;
        if (s != null ? !s.equals(that.s) : that.s != null) return false;
        if (so != null ? !so.equals(that.so) : that.so != null) return false;
        if (io != null ? !io.equals(that.io) : that.io != null) return false;
        if (lo != null ? !lo.equals(that.lo) : that.lo != null) return false;
        return nullableinteger != null ? nullableinteger.equals(that.nullableinteger) : that.nullableinteger == null;

    }

    @Override
    public int hashCode() {
        int result = str != null ? str.hashCode() : 0;
        result = 31 * result + (bool ? 1 : 0);
        result = 31 * result + (boolO != null ? boolO.hashCode() : 0);
        result = 31 * result + (int) b;
        result = 31 * result + (bo != null ? bo.hashCode() : 0);
        result = 31 * result + (s != null ? s.hashCode() : 0);
        result = 31 * result + (so != null ? so.hashCode() : 0);
        result = 31 * result + i;
        result = 31 * result + (io != null ? io.hashCode() : 0);
        result = 31 * result + (int) (l ^ (l >>> 32));
        result = 31 * result + (lo != null ? lo.hashCode() : 0);
        result = 31 * result + (nullableinteger != null ? nullableinteger.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "_ConditionWithProperties{" +
                "str='" + str + '\'' +
                ", bool=" + bool +
                ", boolO=" + boolO +
                ", b=" + b +
                ", bo=" + bo +
                ", s=" + s +
                ", so=" + so +
                ", i=" + i +
                ", io=" + io +
                ", l=" + l +
                ", lo=" + lo +
                ", nullableinteger=" + nullableinteger +
                "} " + super.toString();
    }
}
