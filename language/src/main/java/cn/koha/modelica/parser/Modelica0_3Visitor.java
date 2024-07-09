// Generated from F:/graalmodelica/language/src/main/java/cn/koha/modelica/parser/Modelica0_3.g4 by ANTLR 4.13.1
package cn.koha.modelica.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link Modelica0_3Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface Modelica0_3Visitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#stored_definition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStored_definition(Modelica0_3Parser.Stored_definitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#class_definition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClass_definition(Modelica0_3Parser.Class_definitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#class_specifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClass_specifier(Modelica0_3Parser.Class_specifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#class_prefixes}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClass_prefixes(Modelica0_3Parser.Class_prefixesContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#long_class_specifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLong_class_specifier(Modelica0_3Parser.Long_class_specifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#short_class_specifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShort_class_specifier(Modelica0_3Parser.Short_class_specifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#der_class_specifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDer_class_specifier(Modelica0_3Parser.Der_class_specifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#base_prefix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBase_prefix(Modelica0_3Parser.Base_prefixContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#enum_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnum_list(Modelica0_3Parser.Enum_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#enumeration_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumeration_literal(Modelica0_3Parser.Enumeration_literalContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#composition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComposition(Modelica0_3Parser.CompositionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#language_specification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLanguage_specification(Modelica0_3Parser.Language_specificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#external_function_call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExternal_function_call(Modelica0_3Parser.External_function_callContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#element_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElement_list(Modelica0_3Parser.Element_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#element}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElement(Modelica0_3Parser.ElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#import_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImport_clause(Modelica0_3Parser.Import_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#import_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImport_list(Modelica0_3Parser.Import_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#extends_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExtends_clause(Modelica0_3Parser.Extends_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#constraining_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraining_clause(Modelica0_3Parser.Constraining_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#component_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComponent_clause(Modelica0_3Parser.Component_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#type_prefix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_prefix(Modelica0_3Parser.Type_prefixContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#type_specifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_specifier(Modelica0_3Parser.Type_specifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#component_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComponent_list(Modelica0_3Parser.Component_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#component_declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComponent_declaration(Modelica0_3Parser.Component_declarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#condition_attribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition_attribute(Modelica0_3Parser.Condition_attributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(Modelica0_3Parser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#modification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModification(Modelica0_3Parser.ModificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#class_modification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClass_modification(Modelica0_3Parser.Class_modificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#argument_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgument_list(Modelica0_3Parser.Argument_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#argument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgument(Modelica0_3Parser.ArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#element_modification_or_replaceable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElement_modification_or_replaceable(Modelica0_3Parser.Element_modification_or_replaceableContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#element_modification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElement_modification(Modelica0_3Parser.Element_modificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#element_redeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElement_redeclaration(Modelica0_3Parser.Element_redeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#element_replaceable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElement_replaceable(Modelica0_3Parser.Element_replaceableContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#component_clause1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComponent_clause1(Modelica0_3Parser.Component_clause1Context ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#component_declaration1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComponent_declaration1(Modelica0_3Parser.Component_declaration1Context ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#short_class_definition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShort_class_definition(Modelica0_3Parser.Short_class_definitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#equation_section}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEquation_section(Modelica0_3Parser.Equation_sectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#algorithm_section}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlgorithm_section(Modelica0_3Parser.Algorithm_sectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#equation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEquation(Modelica0_3Parser.EquationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#statement_comma}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement_comma(Modelica0_3Parser.Statement_commaContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(Modelica0_3Parser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#if_equation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_equation(Modelica0_3Parser.If_equationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#if_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_statement(Modelica0_3Parser.If_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#for_equation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor_equation(Modelica0_3Parser.For_equationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#for_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor_statement(Modelica0_3Parser.For_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#for_indices}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor_indices(Modelica0_3Parser.For_indicesContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#for_index}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor_index(Modelica0_3Parser.For_indexContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#while_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_statement(Modelica0_3Parser.While_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#when_equation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhen_equation(Modelica0_3Parser.When_equationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#when_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhen_statement(Modelica0_3Parser.When_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#connect_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConnect_clause(Modelica0_3Parser.Connect_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(Modelica0_3Parser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#simple_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimple_expression(Modelica0_3Parser.Simple_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#logical_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogical_expression(Modelica0_3Parser.Logical_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#logical_term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogical_term(Modelica0_3Parser.Logical_termContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#logical_factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogical_factor(Modelica0_3Parser.Logical_factorContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#relation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelation(Modelica0_3Parser.RelationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#rel_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRel_op(Modelica0_3Parser.Rel_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#arithmetic_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmetic_expression(Modelica0_3Parser.Arithmetic_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#add_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdd_op(Modelica0_3Parser.Add_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(Modelica0_3Parser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#mul_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMul_op(Modelica0_3Parser.Mul_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(Modelica0_3Parser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(Modelica0_3Parser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#array}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray(Modelica0_3Parser.ArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#signed_number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSigned_number(Modelica0_3Parser.Signed_numberContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitName(Modelica0_3Parser.NameContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#component_reference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComponent_reference(Modelica0_3Parser.Component_referenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#function_call_args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_call_args(Modelica0_3Parser.Function_call_argsContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#function_arguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_arguments(Modelica0_3Parser.Function_argumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#function_arguments_non_first}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_arguments_non_first(Modelica0_3Parser.Function_arguments_non_firstContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#array_arguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray_arguments(Modelica0_3Parser.Array_argumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#named_arguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamed_arguments(Modelica0_3Parser.Named_argumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#named_argument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamed_argument(Modelica0_3Parser.Named_argumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#function_argument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_argument(Modelica0_3Parser.Function_argumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#output_expression_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOutput_expression_list(Modelica0_3Parser.Output_expression_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#expression_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression_list(Modelica0_3Parser.Expression_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#array_subscripts}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray_subscripts(Modelica0_3Parser.Array_subscriptsContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#subscript_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscript_(Modelica0_3Parser.Subscript_Context ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#comment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComment(Modelica0_3Parser.CommentContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#string_comment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString_comment(Modelica0_3Parser.String_commentContext ctx);
	/**
	 * Visit a parse tree produced by {@link Modelica0_3Parser#annotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotation(Modelica0_3Parser.AnnotationContext ctx);
}