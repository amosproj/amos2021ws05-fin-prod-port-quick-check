import {
  Flex,
  IconButton,
  ButtonGroup,
  CheckIcon,
  CloseIcon,
  EditIcon,
  useEditableControls,
} from '@chakra-ui/react';
import {
  Editable,
  EditableInput,
  EditablePreview,
  Heading,
  Text,
  Stack,
  HStack,
  VStack,
  Button,
} from '@chakra-ui/react';



export default function ShowEditable(prop) {
    if (prop.editable){
        return  (
            <div>
            <Text>Can Edit</Text>
            <Editable defaultValue={prop.text}>
            <EditablePreview />
            <EditableInput />
            </Editable>
            </div>);
    }
    else{
        return (<Text>{prop.text}</Text>);
    }

}
