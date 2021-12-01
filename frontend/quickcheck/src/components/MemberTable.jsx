import React from 'react';
import {
  Button,
  HStack,
  Text,
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

function AddButton(props) {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [email, setEmail] = useState('');
  const [role, setRole] = useState('Client');
  const header = 'Add new Member'
  return (
    <>
      <IconButton icon={<AddIcon />} colorScheme="green" size="lg" {...props} onClick={onOpen} />

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
              onChange={setRole}
            />
          </ModalBody>

          <ModalFooter py={5} px={10}>
            <Button
              colorScheme="blue"
              mr={3}
              onClick={(e) => {
                props.onAddMember({ email: email, role: role });
                onClose();
              }}
            >
              Save
            </Button>
            <Button onClick={onClose}>Cancel</Button>
          </ModalFooter>
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

function MemberHead({ editable, addButton }) {
  return (
    <HStack px={4} rounded="md" align="center" spacing={3} mb={5}>
      <Heading size="md" w="50%" bg="gray.600" p={2} pb={5} rounded="md">
        Email
      </Heading>
      <Heading size="md" minW={36} w={48} bg="gray.600" p={2} pb={5} rounded="md">
        Role
      </Heading>
      {editable ? addButton : <div />}
    </HStack>
  );
}

function MemberRow({ editable, member, onChangeRole, removeButton }) {
  return (
    <HStack px={4} rounded="md" align="center" spacing={3}>
      <Text w="50%" bg="blue.700" rounded="md" p={2} px={3} align="left">
        {member.email}
      </Text>
      {editable ? (
        <Selection
          selected={member.role}
          options={Object.values(roles)}
          onChange={onChangeRole}
          minW={36}
          w={48}
          bg="blue.700"
        />
      ) : (
        <Text minW={36} w={48} rounded="md" bg="blue.700" p={2} px={3} h="full" align="left">
          {member.role}
        </Text>
      )}
      {editable ? removeButton : <div />}
    </HStack>
  );
}

// Assumption: ProjectMembers is a list of object: {id, role}
export default function MemberTable({ editable, members, handleChange }) {
  const handleRemoveMember = (member) => () => {
    const newMembers = members.filter(m => (m.email !== member.email))
    handleChange(newMembers);
  };

  const handleAddMember = (newMember) => {
    handleChange([...members, newMember]);
  };

  const handleRoleChange = (member) => (newRole) => {   // This is a curried function in JS
    let index = members.map(m => m.email).indexOf(member.email);
    members[index] = { ...member, role: newRole };
    handleChange(members);
  };

  return (
    <List spacing={2} direction="column" minW="80%" align="center" pb={5}>
      <MemberHead
        editable={editable}
        addButton={<AddButton w={16} onAddMember={handleAddMember}></AddButton>}
      />
      {members.map((member) => (
        <MemberRow
          key={member.email}
          member={member}
          editable={editable}
          onChangeRole={handleRoleChange(member)}
          removeButton={<RemoveButton onRemove={handleRemoveMember(member)} />}
        ></MemberRow>
      ))}
    </List>
  );
}
