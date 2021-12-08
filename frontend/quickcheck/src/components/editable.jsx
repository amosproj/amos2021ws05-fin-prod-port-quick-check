import { Editable, EditableInput, EditablePreview, Text } from '@chakra-ui/react';

export default function ShowEditable(prop) {
  if (prop.editable) {
    return (
      <>
        <Editable defaultValue={prop.text}>
          <EditablePreview />
          <EditableInput />
        </Editable>
      </>
    );
  } else {
    return <Text>{prop.text}</Text>;
  }
}
