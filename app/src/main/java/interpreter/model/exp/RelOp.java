package interpreter.model.exp;

public enum RelOp {
    SMALLER_THAN("<"),SMALLER_OR_EQUAL("<="),EQUAL_TO("=="),NOT_EQUAL_TO("!="), BIGGER_THAN(">"), BIGGGER_OR_EQUAL_TO(">=");
    private final String symbol;
    RelOp(String symbol){
        this.symbol=symbol;
    }
    @Override public String toString(){
        return symbol;
    }

}
