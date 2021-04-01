package nl.han.ica.icss.parser;

import nl.han.ica.datastructures.HANStack;
import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.*;
import nl.han.ica.icss.ast.selectors.*;

/**
 * This class extracts the ICSS abstract syntax tree from the ANTLR parse tree.
 */
public class ASTListener extends ICSSBaseListener {
    /**
     * The AST object.
     */
    private final AST ast;

    /**
     * A container used to keep track of the parent nodes when recursively traversing the AST object.
     */
    private final IHANStack<ASTNode> currentContainer;

    /**
     * Create and initialize an instance of the AST listener.
     */
    public ASTListener() {
        ast = new AST();
        currentContainer = new HANStack<>();
    }

    /**
     * Get the AST object.
     * @return The AST object.
     */
    public AST getAST() {
        return ast;
    }

    @Override
    public void enterStylerule(ICSSParser.StyleruleContext context) {
        currentContainer.push(new Stylerule());
    }

    @Override
    public void exitStylerule(ICSSParser.StyleruleContext context) {
        Stylerule stylerule = (Stylerule) currentContainer.pop();
        ast.root.addChild(stylerule);
    }

    @Override
    public void enterSelector(ICSSParser.SelectorContext context) {
        Selector selector = null;

        if (context.classSelector() != null) {
            selector = new ClassSelector(context.getText());
        } else if (context.idSelector() != null) {
            selector = new IdSelector(context.getText());
        } else if (context.tagSelector() != null) {
            selector = new TagSelector(context.getText());
        }

        currentContainer.push(selector);
    }

    @Override
    public void exitSelector(ICSSParser.SelectorContext context) {
        Selector selector = (Selector) currentContainer.pop();
        currentContainer.peek().addChild(selector);
    }

    @Override
    public void enterLiteral(ICSSParser.LiteralContext context) {
        Literal literal = null;

        if (context.bool() != null) {
            literal = new BoolLiteral(context.getText());
        } else if (context.color() != null) {
            literal = new ColorLiteral(context.getText());
        } else if (context.percentage() != null) {
            literal = new PercentageLiteral(context.getText());
        } else if (context.pixel() != null) {
            literal = new PixelLiteral(context.getText());
        } else if (context.scalar() != null) {
            literal = new ScalarLiteral(context.getText());
        }

        currentContainer.peek().addChild(literal);
    }

    @Override
    public void enterVariableAssignment(ICSSParser.VariableAssignmentContext context) {
        currentContainer.push(new VariableAssignment());
    }

    @Override
    public void exitVariableAssignment(ICSSParser.VariableAssignmentContext context) {
        VariableAssignment variableAssignment = (VariableAssignment) currentContainer.pop();

        try {
            currentContainer.peek().addChild(variableAssignment);
        } catch (IndexOutOfBoundsException e) {
            ast.root.addChild(variableAssignment);
        }
    }

    @Override
    public void enterVariableReference(ICSSParser.VariableReferenceContext context) {
        currentContainer.peek().addChild(new VariableReference(context.getText()));
    }

    @Override
    public void enterDeclaration(ICSSParser.DeclarationContext context) {
        currentContainer.push(new Declaration());
    }

    @Override
    public void exitDeclaration(ICSSParser.DeclarationContext context) {
        Declaration declaration = (Declaration) currentContainer.pop();
        currentContainer.peek().addChild(declaration);
    }

    @Override
    public void exitPropertyName(ICSSParser.PropertyNameContext context) {
        currentContainer.peek().addChild(new PropertyName(context.getText()));
    }

    @Override
    public void enterIfClause(ICSSParser.IfClauseContext context) {
        currentContainer.push(new IfClause());
    }

    @Override
    public void exitIfClause(ICSSParser.IfClauseContext context) {
        IfClause ifClause = (IfClause) currentContainer.pop();
        currentContainer.peek().addChild(ifClause);
    }

    @Override
    public void enterElseClause(ICSSParser.ElseClauseContext context) {
        currentContainer.push(new ElseClause());
    }

    @Override
    public void exitElseClause(ICSSParser.ElseClauseContext context) {
        ElseClause elseClause = (ElseClause) currentContainer.pop();
        currentContainer.peek().addChild(elseClause);
    }

    @Override
    public void enterRandom(ICSSParser.RandomContext context) {
        Operation operation = new RandomOperation();
        currentContainer.peek().addChild(operation);
        currentContainer.push(operation);
    }

    @Override
    public void exitRandom(ICSSParser.RandomContext context) {
        currentContainer.pop();
    }

    @Override
    public void enterRgb(ICSSParser.RgbContext context) {
        Operation operation = new RgbOperation();
        currentContainer.peek().addChild(operation);
        currentContainer.push(operation);
    }

    @Override
    public void exitRgb(ICSSParser.RgbContext context) {
        currentContainer.pop();
    }

    @Override
    public void enterHsv(ICSSParser.HsvContext context) {
        Operation operation = new HsvOperation();
        currentContainer.peek().addChild(operation);
        currentContainer.push(operation);
    }

    @Override
    public void exitHsv(ICSSParser.HsvContext context) {
        currentContainer.pop();
    }

    @Override
    public void enterNegation(ICSSParser.NegationContext context) {
        Operation operation = new NegateOperation();
        currentContainer.peek().addChild(operation);
        currentContainer.push(operation);
    }

    @Override
    public void exitNegation(ICSSParser.NegationContext context) {
        currentContainer.pop();
    }

    @Override
    public void enterMinus(ICSSParser.MinusContext context) {
        Operation operation = new MinusOperation();
        currentContainer.peek().addChild(operation);
        currentContainer.push(operation);
    }

    @Override
    public void exitMinus(ICSSParser.MinusContext context) {
        currentContainer.pop();
    }

    @Override
    public void enterMultiplication(ICSSParser.MultiplicationContext context) {
        Operation operation = new MultiplyOperation();
        currentContainer.peek().addChild(operation);
        currentContainer.push(operation);
    }

    @Override
    public void exitMultiplication(ICSSParser.MultiplicationContext context) {
        currentContainer.pop();
    }

    @Override
    public void enterAddition(ICSSParser.AdditionContext context) {
        Operation operation = new AddOperation();
        currentContainer.peek().addChild(operation);
        currentContainer.push(operation);
    }

    @Override
    public void exitAddition(ICSSParser.AdditionContext context) {
        currentContainer.pop();
    }

    @Override
    public void enterSubtraction(ICSSParser.SubtractionContext context) {
        Operation operation = new SubtractOperation();
        currentContainer.peek().addChild(operation);
        currentContainer.push(operation);
    }

    @Override
    public void exitSubtraction(ICSSParser.SubtractionContext context) {
        currentContainer.pop();
    }

    @Override
    public void enterConditional(ICSSParser.ConditionalContext context) {
        Operation operation = new ConditionalOperation();
        currentContainer.peek().addChild(operation);
        currentContainer.push(operation);
    }

    @Override
    public void exitConditional(ICSSParser.ConditionalContext context) {
        currentContainer.pop();
    }

    @Override
    public void enterSpaceship(ICSSParser.SpaceshipContext context) {
        Operation operation = new SpaceshipOperation();
        currentContainer.peek().addChild(operation);
        currentContainer.push(operation);
    }

    @Override
    public void exitSpaceship(ICSSParser.SpaceshipContext context) {
        currentContainer.pop();
    }

    @Override
    public void enterLessThan(ICSSParser.LessThanContext context) {
        Operation operation = new LessThanOperation();
        currentContainer.peek().addChild(operation);
        currentContainer.push(operation);
    }

    @Override
    public void exitLessThan(ICSSParser.LessThanContext context) {
        currentContainer.pop();
    }

    @Override
    public void enterLessThanOrEqual(ICSSParser.LessThanOrEqualContext context) {
        Operation operation = new LessThanOrEqualOperation();
        currentContainer.peek().addChild(operation);
        currentContainer.push(operation);
    }

    @Override
    public void exitLessThanOrEqual(ICSSParser.LessThanOrEqualContext context) {
        currentContainer.pop();
    }

    @Override
    public void enterGreaterThan(ICSSParser.GreaterThanContext context) {
        Operation operation = new GreaterThanOperation();
        currentContainer.peek().addChild(operation);
        currentContainer.push(operation);
    }

    @Override
    public void exitGreaterThan(ICSSParser.GreaterThanContext context) {
        currentContainer.pop();
    }

    @Override
    public void enterGreaterThanOrEqual(ICSSParser.GreaterThanOrEqualContext context) {
        Operation operation = new GreaterThanOrEqualOperation();
        currentContainer.peek().addChild(operation);
        currentContainer.push(operation);
    }

    @Override
    public void exitGreaterThanOrEqual(ICSSParser.GreaterThanOrEqualContext context) {
        currentContainer.pop();
    }

    @Override
    public void enterEqual(ICSSParser.EqualContext context) {
        Operation operation = new EqualOperation();
        currentContainer.peek().addChild(operation);
        currentContainer.push(operation);
    }

    @Override
    public void exitEqual(ICSSParser.EqualContext context) {
        currentContainer.pop();
    }

    @Override
    public void enterUnequal(ICSSParser.UnequalContext context) {
        Operation operation = new UnequalOperation();
        currentContainer.peek().addChild(operation);
        currentContainer.push(operation);
    }

    @Override
    public void exitUnequal(ICSSParser.UnequalContext context) {
        currentContainer.pop();
    }

    @Override
    public void enterAnd(ICSSParser.AndContext context) {
        Operation operation = new AndOperation();
        currentContainer.peek().addChild(operation);
        currentContainer.push(operation);
    }

    @Override
    public void exitAnd(ICSSParser.AndContext context) {
        currentContainer.pop();
    }

    @Override
    public void enterOr(ICSSParser.OrContext context) {
        Operation operation = new OrOperation();
        currentContainer.peek().addChild(operation);
        currentContainer.push(operation);
    }

    @Override
    public void exitOr(ICSSParser.OrContext context) {
        currentContainer.pop();
    }
}
