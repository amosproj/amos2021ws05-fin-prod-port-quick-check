import { Select, Input, Text, Box } from '@chakra-ui/react';

export function Selection({ options, selected, ...rest }) {
  // required args: options, selected (option), onChange :updateFunction
  return (
    <Select {...rest} value={selected}>
      {options.map((opt) => (
        <option value={opt} key={opt}>
          {opt}
        </option>
      ))}
    </Select>
  );
}

// pass value and onchange just like with a regular input
export function ConditionalInput({ editable, value, onChange, fontStyle, ...rest }) {
  return (
    <Box align="center" {...rest} rounded="md">
      {editable ? (
        <Input value={value} onChange={onChange} {...fontStyle} />
      ) : (
        <Text align="left" px={4} py={2} {...fontStyle}>
          {value}
        </Text>
      )}
    </Box>
  );
}
