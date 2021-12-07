import React from 'react';
import {
  Button,
  useDisclosure,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalFooter,
  ModalCloseButton,
  FormControl,
  FormLabel,
  Input,
  ModalBody,
  ModalHeader,
  IconButton,
} from '@chakra-ui/react';
import { roles } from '../utils/const';
import { Selection } from './Inputs.jsx';

import { AddIcon } from '@chakra-ui/icons';
import { useState } from 'react';

export default function AddButton(props) {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [email, setEmail] = useState('');
  const [role, setRole] = useState('Client');
  const header = 'Add new Member';
  return (
    <>
      <IconButton icon={<AddIcon />} variant="primary" size="lg" {...props} onClick={onOpen} />

      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader color="primary">{header}</ModalHeader>
          <ModalCloseButton />
          <ModalBody px={10}>
            <FormControl>
              <FormLabel pl={3}>Email</FormLabel>
              <Input mb={6} placeholder="Email" onChange={(e) => setEmail(e.target.value)} />
            </FormControl>
            <Selection
              options={Object.values(roles)}
              selected={roles.consultant}
              onChange={setRole}
            />
          </ModalBody>

          <ModalFooter py={5} px={10}>
            <Button
              variant="primary"
              mx={3}
              onClick={(e) => {
                props.onAddMember({ email: email, role: role });
                onClose();
              }}
            >
              Save
            </Button>
            <Button onClick={onClose} variant="wisper">
              Cancel
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
}
