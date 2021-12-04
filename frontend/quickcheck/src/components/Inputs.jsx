import { Select, Input, Text, Box } from '@chakra-ui/react';

export function Selection(props) {
  // required args: options, selected (option), onChange :updateFunction
  return (
    <Select {...props}>
      {props.options.map((opt) => (
        <option selected={opt === props.selected} value={opt} key={opt}>
          {opt}
        </option>
      ))}
    </Select>
  );
}

// pass value and onchange just like with a regular input
export function ConditionalInput(props) {
  return (
    <Box align="center" {...props} rounded="md">
      {props.editable ? (
        <Input value={props.value} onChange={props.onChange} {...props.fontStyle} />
      ) : (
        <Text align="left" px={4} py={2} {...props.fontStyle}>
          {props.value}
        </Text>
      )}
    </Box>
  );
}
