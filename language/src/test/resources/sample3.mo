model RLCCircuit
  parameter Real R = 1 "Resistance";
  parameter Real L = 0.5 "Inductance";
  parameter Real C = 1 "Capacitance";
  Real i "Current";
  Real v "Voltage";
equation
  der(i) = (v - i*R)/L;
  der(v) = i/C;
initial equation
  i = 0;
  v = 0;
end RLCCircuit;
