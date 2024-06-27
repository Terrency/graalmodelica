model ChemicalReaction
  parameter Real k1 = 0.1 "Rate constant 1";
  parameter Real k2 = 0.05 "Rate constant 2";
  Real reactantA "Concentration of reactant A";
  Real reactantB "Concentration of reactant B";
  Real product "Concentration of product";
equation
  der(reactantA) = -k1 * reactantA;
  der(reactantB) = -k2 * reactantB;
  der(product) = k1 * reactantA + k2 * reactantB;
initial equation
  reactantA = 1;
  reactantB = 1;
  product = 0;
end ChemicalReaction;
