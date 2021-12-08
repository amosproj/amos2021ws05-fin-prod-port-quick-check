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
import { AddIcon } from '@chakra-ui/icons';
import { useState } from 'react';
import { roles } from '../../utils/const';

import Selection from '../Selection.jsx';


export default function AddMemberButton(props) {
    const { isOpen, onOpen, onClose } = useDisclosure();
    const [email, setEmail] = useState('');
    const [role, setRole] = useState('Client');
    const header = 'Add new Member';
    return (
      <>
        <IconButton icon={<AddIcon />} {...props} onClick={onOpen}/>
  
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
              <Button onClick={onClose} variant="whisper">
                Cancel
              </Button>
            </ModalFooter>
          </ModalContent>
        </Modal>
      </>
    );
  }