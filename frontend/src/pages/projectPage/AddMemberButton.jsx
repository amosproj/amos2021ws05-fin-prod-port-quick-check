import React from 'react';
import {
  Button,
  useDisclosure,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalFooter,
  ModalCloseButton,
  Input,
  ModalBody,
  ModalHeader,
  IconButton,
  InputGroup,
  InputLeftElement,
} from '@chakra-ui/react';
import { AddIcon, EmailIcon } from '@chakra-ui/icons';
import { useState, useEffect } from 'react';
import { useStoreActions } from 'easy-peasy';

import { roles } from '../../utils/const';
import Selection from '../../components/Selection.jsx';
import { api } from '../../utils/apiClient';

/**
 * Popup button for adding member
 */
export default function AddMemberButton(buttonProps) {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const addMember = useStoreActions((actions) => actions.project.addMember);

  const [email, setEmail] = useState('');
  const [role, setRole] = useState(roles[0]);
  const header = 'Add new Member';

  const [allUsers, setAllUsers] = useState([]);

  const checkMemberExists = (email) => {
    return allUsers.map((u) => u.userEmail).includes(email);
  };

  useEffect(() => {
    api.url('/users').get().json(setAllUsers);
  }, []);

  return (
    <>
      <IconButton
        icon={<AddIcon />}
        {...buttonProps}
        aria-label="Add new Member"
        onClick={onOpen}
      />

      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader color="primary">{header}</ModalHeader>
          <ModalCloseButton />
          <ModalBody px={10}>
            <InputGroup aria-label="Email Input">
              <Input
                maxLength={60}
                mb={6}
                placeholder="Email"
                onChange={(e) => setEmail(e.target.value)}
              />
              <InputLeftElement>
                <EmailIcon />
              </InputLeftElement>
            </InputGroup>
            <Selection options={roles} selected={role} onChange={setRole} />
          </ModalBody>

          <ModalFooter py={5} px={10}>
            <Button
              variant="primary"
              mx={3}
              onClick={(e) => {
                if (checkMemberExists(email)) {
                  addMember({ userEmail: email, role: role });
                  onClose();
                } else {
                  alert('User does not exist');
                }
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
