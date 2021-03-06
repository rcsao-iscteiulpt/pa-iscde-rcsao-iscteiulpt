package pa.iscde.docGeneration;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;


import org.eclipse.jdt.core.dom.TypeDeclaration;

import InfoClasses.ConstructorInfo;
import InfoClasses.FieldInfo;
import InfoClasses.MethodInfo;
import InfoClasses.Modifiers;

/**
 * Class that extends ASTVisitor to get information about a given class <br>
 * It stores information about it's methods, fields and constructors in Lists <br>
 * Like names, parameters, ReturnType, Modifiers, etc...
 * @author Ricardo Silva
 *
 */
public class ClassInfoChecker extends ASTVisitor {

	private final HashMap<String, Object> classbasicinfo = new HashMap<String, Object>();
	private final ArrayList<FieldInfo> classfields = new ArrayList<FieldInfo>();
	private final ArrayList<ConstructorInfo> classconstructors = new ArrayList<ConstructorInfo>();
	private final ArrayList<MethodInfo> classmethods = new ArrayList<MethodInfo>();

	/**
	 * Method used for visiting the class itself and stores it's info on the HashMap
	 * @param TypeDeclaration node, node containing info about the class itself 
	 */
	@Override
	public boolean visit(TypeDeclaration node) {
		classbasicinfo.put("Modifiers", getModifiersList(node.getModifiers()));
		classbasicinfo.put("ClassName", node.getName().toString());
		return true;
	}

	/**
	 * Method used to visit class methods/constructors and stores info on the method/constructor list 
	 * @param MethodDeclaration node, node containing info about the visited class method 
	 */
	@Override
	public boolean visit(MethodDeclaration node) {
		if(node.isConstructor()) {
			classconstructors.add(new ConstructorInfo(node, getModifiersList(node.getModifiers())));
		} else {
			classmethods.add(new MethodInfo(node, getModifiersList(node.getModifiers())));
		}
		return true; // false to avoid child VariableDeclarationFragment to be processed again
	}

	/**
	 * Method used to visit class fields and stores it's info on the field list
	 * @param MethodDeclaration node, node containing info about the visited class method 
	 */
	@Override
	public boolean visit(FieldDeclaration node) {
		classfields.add(new FieldInfo(node, getModifiersList(node.getModifiers())));
		return false; // false to avoid child VariableDeclarationFragment to be processed again
	}

	// visits variable declarations in parameters
	/*
	@Override
	public boolean visit(SingleVariableDeclaration node) {
		String name = node.getName().toString();
		return true;
	}
	*/

	/**
	 * Getter which will get all modifiers according the given integer
	 * @param modifiers int which display modifiers explicity
	 * @return list of enums corresponding to the given object modifiers
	 */
	public ArrayList<Modifiers> getModifiersList(int modifiers) {
		ArrayList<Modifiers> modifierslist = new ArrayList<Modifiers>();

		if (Modifier.isPublic(modifiers))
			modifierslist.add(Modifiers.Public);
		if (Modifier.isProtected(modifiers))
			modifierslist.add(Modifiers.Protected);
		if (Modifier.isPrivate(modifiers))
			modifierslist.add(Modifiers.Private);
		if (Modifier.isAbstract(modifiers))
			modifierslist.add(Modifiers.Abstract);
		if (Modifier.isStatic(modifiers))
			modifierslist.add(Modifiers.Static);
		if (Modifier.isFinal(modifiers))
			modifierslist.add(Modifiers.Final);
		if (Modifier.isSynchronized(modifiers))
			modifierslist.add(Modifiers.Synchronized);

		return modifierslist;

	}
	
	public HashMap<String, Object> getClassbasicinfo() {
		return classbasicinfo;
	}

	public ArrayList<FieldInfo> getClassfields() {
		return classfields;
	}

	public ArrayList<ConstructorInfo> getClassconstructors() {
		return classconstructors;
	}

	public ArrayList<MethodInfo> getClassmethods() {
		return classmethods;
	}



	

	
	
	
}
