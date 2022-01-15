import { createStandaloneToast } from '@chakra-ui/react';
// documentation: https://chakra-ui.com/docs/feedback/toast

export function notification(title, message, status = 'error') {
  // possible status values:  info | warning | success | error
  const toast = createStandaloneToast();

  toast({
    title: title,
    description: message,
    status: status,
    duration: 5000,
    isClosable: true,
  });
}
