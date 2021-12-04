import React from 'react';
import {
  Button,
  HStack,
  Box,
  Text,
  useDisclosure,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalCloseButton,
  FormControl,
  FormLabel,
  Input,
  ModalBody,
  ModalHeader,
  IconButton,
  Popover,
  PopoverTrigger,
  Heading,
  PopoverContent,
  PopoverHeader,
  PopoverBody,
  List,
} from '@chakra-ui/react';
import { DeleteIcon, AddIcon } from '@chakra-ui/icons';
import { useState } from 'react';
import { roles } from '../utils/const';
import { Selection } from './Inputs.jsx';
import { useStoreActions, useStoreState } from 'easy-peasy';

function AddButton({ onAddMember }) {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [email, setEmail] = useState('');
  const [role, setRole] = useState(roles.consultant);
  const header = 'Add new Member';
  return (
    <>
      <IconButton icon={<AddIcon />} colorScheme="green" size="lg" onClick={onOpen} />

      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader color="teal.300">{header}</ModalHeader>
          <ModalCloseButton />
          <ModalBody px={10}>
            <FormControl>
              <FormLabel pl={3}>Email</FormLabel>
              <Input mb={6} placeholder="Email" onChange={(e) => setEmail(e.target.value)} />
            </FormControl>
            <Selection
              options={Object.values(roles)}
              selected={roles.consultant}
              onChange={(e) => setRole(e.target.value)}
            />

            <Box align="right" pt={8} pb={2}>
              <Button
                colorScheme="blue"
                mr={3}
                onClick={(e) => {
                  onAddMember({ email: email, role: role });
                  onClose();
                }}
              >
                Save
              </Button>
              <Button onClick={onClose}>Cancel</Button>
            </Box>
          </ModalBody>
        </ModalContent>
      </Modal>
    </>
  );
}

function RemoveButton({ onRemove }) {
  const { onOpen, onClose, isOpen } = useDisclosure();
  return (
    <Popover isOpen={isOpen} onOpen={onOpen} onClose={onClose} isLazy={true} w="wrap">
      <PopoverTrigger>
        <IconButton
          icon={<DeleteIcon />}
          onClick={onOpen}
          size="md"
          color="red.900"
          bg="red.400"
          w={16}
        />
      </PopoverTrigger>
      <PopoverContent>
        <PopoverHeader fontWeight="semibold">Confirm removing this User</PopoverHeader>
        <PopoverBody>
          <Button
            colorScheme="red"
            mx={1}
            onClick={(e) => {
              onRemove();
              onClose();
            }}
          >
            Remove
          </Button>
          <Button mx={1} onClick={onClose}>
            Cancel
          </Button>
        </PopoverBody>
      </PopoverContent>
    </Popover>
  );
}

function MemberHead({ editMode, addButton }) {
  return (
    <HStack px={4} rounded="md" align="center" spacing={3} mb={5}>
      <Heading size="md" w="50%" bg="gray.600" p={2} pb={5} rounded="md">
        Email
      </Heading>
      <Heading size="md" minW={36} w={48} bg="gray.600" p={2} pb={5} rounded="md">
        Role
      </Heading>
      {editMode ? addButton : <div />}
    </HStack>
  );
}

function MemberRow({ editMode, member, onChangeRole, removeButton }) {
  return (
    <HStack px={4} rounded="md" align="center" spacing={3}>
      <Text w="50%" bg="blue.700" rounded="md" p={2} px={3} align="left">
        {member.email}
      </Text>
      {editMode ? (
        <Selection
          selected={member.role}
          options={Object.values(roles)}
          onChange={(e) => onChangeRole(e.target.value)}
          minW={36}
          w={48}
          bg="blue.700"
        />
      ) : (
        <Text minW={36} w={48} rounded="md" bg="blue.700" p={2} px={3} h="full" align="left">
          {member.role}
        </Text>
      )}
      {editMode ? removeButton : <div />}
    </HStack>
  );
}

// Assumption: ProjectMembers is a list of object: {id, role}
export default function MemberTable({ editMode }) {
  const members = useStoreState((state) => state.project.members);
  const updateProject = useStoreActions((actions) => actions.updateProject);
  const updateMembers = (members) => updateProject({ members: members });

  const handleRemoveMember = (member) => () => {
    const newMembers = members.filter((m) => m.email !== member.email);
    updateMembers(newMembers);
  };

  const addMember = (newMember) => {
    updateMembers([...members, newMember]);
  };

  const changeMemberRole = (member) => (newRole) => {
    // This is a curried function in JS
    let index = members.map((m) => m.email).indexOf(member.email);
    members[index] = { ...member, role: newRole };
    updateMembers(members);
  };

  return (
    <List spacing={2} direction="column" minW="80%" align="center" pb={5}>
      <MemberHead
        editMode={editMode}
        addButton={<AddButton w={16} onAddMember={addMember}></AddButton>}
      />
      {members.map((member) => (
        <MemberRow
          key={member.email}
          member={member}
          editMode={editMode}
          onChangeRole={changeMemberRole(member)}
          removeButton={<RemoveButton onRemove={handleRemoveMember(member)} />}
        ></MemberRow>
      ))}
    </List>
  );
}
