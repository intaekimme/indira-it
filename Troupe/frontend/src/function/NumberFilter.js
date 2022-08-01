export default function numberFilter(number) {
  const intN = parseInt(number);
  let result = intN.toString();
  if (intN > 1000000) {
    result =
      parseInt(intN / 1000000) + "." + (parseInt(intN / 100000) % 10) + "m";
  } else if (intN > 1000) {
    result = parseInt(intN / 1000) + "." + (parseInt(intN / 100) % 10) + "k";
  }
  return result;
}
