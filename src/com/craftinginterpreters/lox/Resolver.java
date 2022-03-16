package com.craftinginterpreters.lox;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Resolver implements Expr.Visitor, Stmt.Visitor {
    private final Interpreter interpreter;
    private final Stack<Map<String, Boolean>> scopes = new Stack<>();

    public Resolver(Interpreter interpreter) {
        this.interpreter = interpreter;
    }
    
    @Override
    public Void visitBlockStmt(Stmt.Block stmt) {
        beginScope();
        resolve(stmt.statements);
        endScope();
        return null;
    }

    void resolve(List<Stmt> statements) {
        for (Stmt statement : statements) {
            resolve(statement);
        }
    }

    void resolve(Stmt stmt) {
        stmt.accept(this);
    }
    
    void resolve(Expr expr) {
        expr.accept(this);
    }
    
    private void beginScope() {
        scopes.push(new HashMap<>());
    }

    private void endScope() {
        scopes.pop();
    }
}
