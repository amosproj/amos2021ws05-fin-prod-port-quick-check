import { Select } from '@chakra-ui/select';

export function Selection(props) {
  // required args: options, selected (option), onChange :updateFunction
  return (
    <Select {...props} onChange={(e) => props.onChange(e.target.value)}>
      {props.options.map((opt) => (
        <option selected={opt === props.selected} value={opt}>
          {opt}
        </option>
      ))}
    </Select>
  );
}
