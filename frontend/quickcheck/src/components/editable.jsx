import {
  Flex,
  IconButton,
  ButtonGroup,
  CheckIcon,
  CloseIcon,
  EditIcon,
  useEditableControls,
} from '@chakra-ui/react';

/* Here's a custom control */
export default function EditableControls() {
  const { isEditing, getSubmitButtonProps, getCancelButtonProps, getEditButtonProps } =
    useEditableControls();

  return isEditing ? (
    <ButtonGroup justifyContent="center" size="sm">
      <IconButton icon={<CheckIcon />} {...getSubmitButtonProps()} />
      <IconButton icon={<CloseIcon />} {...getCancelButtonProps()} />
    </ButtonGroup>
  ) : (
    <Flex justifyContent="center">
      <IconButton size="sm" icon={<EditIcon />} {...getEditButtonProps()} />
    </Flex>
  );
}
