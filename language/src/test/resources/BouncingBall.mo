model BouncingBall
 parameter Real e = 0.8 "Coefficient of restitution";
 parameter BouncingBall_Height h0 = 1.0 "Initial height";
 BouncingBall_Height h;
 BouncingBall_Velocity v;
initial equation
 h = h0;
equation
 v = der(h);
 der(v) = -9.81;
 when h < 0 then
  reinit(v, (- e) * pre(v));
 end when;

public
 type StateSelect = enumeration(never "Do not use as state at all.", avoid "Use as state, if it cannot be avoided (but only if variable appears differentiated and no other potential state with attribute default, prefer, or always can be selected).", default "Use as state if appropriate, but only if variable appears differentiated.", prefer "Prefer it as state over those having the default value (also variables can be selected, which do not appear differentiated).", always "Do use it as a state.");

 type BouncingBall_Height = Real(unit = "m");
 type BouncingBall_Velocity = Real(unit = "m/s");
end BouncingBall;
