import React from 'react';
import {
  useDisclosure,
  Button,
  Popover,
  PopoverTrigger,
  PopoverContent,
  PopoverHeader,
  PopoverBody,
} from '@chakra-ui/react';

// wraps clickable element and shows a confirm prompt to the user
export default function ConfirmClick({ onConfirm, confirmPrompt, children }) {
  const { onOpen, onClose, isOpen } = useDisclosure();
  return (
    <Popover isOpen={isOpen} onOpen={onOpen} onClose={onClose} isLazy={true} w="wrap">
      <PopoverTrigger>{React.cloneElement(children, { onClick: onClose })}</PopoverTrigger>
      <PopoverContent>
        <PopoverHeader fontWeight="semibold">{confirmPrompt}</PopoverHeader>
        <PopoverBody>
          <Button
            variant="secondary"
            mx={1}
            onClick={(e) => {
              onConfirm();
              onClose();
            }}
          >
            Confirm
          </Button>
          <Button variant="whisper" mx={1} onClick={onClose}>
            Cancel
          </Button>
        </PopoverBody>
      </PopoverContent>
    </Popover>
  );
}
