model ParticleMotion
  parameter Real v0 = 10 "Initial velocity";
  parameter Real a = -9.8 "Acceleration due to gravity";
  Real x "Position";
  Real v "Velocity";
  Real t "Time";
equation
  der(x) = v;
  der(v) = a;
  der(t) = 1;
initial equation
  x = 0;
  v = v0;
  t = 0;
end ParticleMotion;
