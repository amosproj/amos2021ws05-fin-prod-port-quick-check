import { Select } from '@chakra-ui/select';

export default function Selection({ options, selected, onChange, ...rest }) {
  // required args: options, selected (option), onChange :updateFunction
  return (
    <Select {...rest} value={selected} onChange={(e) => onChange(e.target.value)}>
      {options.map((opt) => (
        <option value={opt} key={opt}>
          {opt}
        </option>
      ))}
    </Select>
  );
}
