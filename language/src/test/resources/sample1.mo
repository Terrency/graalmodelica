model TemperatureConversion
  parameter Real celsius = 25 "Temperature in Celsius";
  output Real fahrenheit "Temperature in Fahrenheit";
equation
  fahrenheit = (9 / 5) * celsius + 32;
end TemperatureConversion;