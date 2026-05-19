package com.sv;

import java.util.ArrayList;
import java.util.List;

public class ASTNode {
    public String type;
    public String value;
    public List<ASTNode> children;

    public ASTNode(String type, String value) {
        this.type = type;
        this.value = value;
        this.children = new ArrayList<>();
    }
}
