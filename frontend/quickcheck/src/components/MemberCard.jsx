import React from 'react';
import {
  Button,
  Select,
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
import Card from './Card.jsx';
import { DeleteIcon, AddIcon } from '@chakra-ui/icons';
import { useState } from 'react';
import { roles } from '../utils/const';
import { Selection } from './Inputs.jsx';

function AddButton(props) {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [email, setEmail] = useState('');
  const [role, setRole] = useState('Client');
  return (
    <>
      <IconButton icon={<AddIcon />} colorScheme="green" size="lg" {...props} onClick={onOpen} />

      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Add new Member</ModalHeader>
          <ModalCloseButton />
          <ModalBody pb={6}>
            <FormControl>
              <FormLabel pl={3}>Email</FormLabel>
              <Input placeholder="Email" onChange={(e) => setEmail(e.target.value)} />
            </FormControl>
          </ModalBody>
          <ModalBody pb={6}>
            <Select onChange={(e) => setRole(e.target.value)}>
              <option selected value="Client">
                Client
              </option>
              <option value="Project Manager">Project Manager</option>
              <option value="Project Owner">Project Owner</option>
            </Select>
          </ModalBody>

          <ModalFooter>
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
    <HStack px={4} rounded="md" align="center" spacing={5}>
      <Heading size="md" w="50%" bg="gray.600" p={3} rounded="md">
        Email
      </Heading>
      <Heading size="md" minW={36} w={48} bg="gray.600" p={3} rounded="md">
        Role
      </Heading>
      {editable ? addButton : <div />}
    </HStack>
  );
}

function MemberRow({ editable, member, onChangeRole, removeButton }) {
  const rolesArray = Object.values(roles);

  return (
    <HStack px={4} rounded="md" align="center" spacing={5}>
      <Text w="50%" bg="blue.700" rounded="md" p={2} px={3} align="left">
        {member.email}
      </Text>
      {editable ? (
        <Selection
          selected={member.role}
          options={rolesArray}
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
    const newMembers = members.filter((m) => {
      return m.email !== member.email;
    });
    handleChange(newMembers);
  };

  const handleAddMember = (newMember) => {
    handleChange([...members, newMember]);
  };

  const handleRoleChange = (member) => (newRole) => {
    // This is a curried function in JS
    // the state is updated, however it is somehow not rendered
    let index = members.findIndex((m) => m.email === member.email);

    console.log('index', index);
    members[index] = { ...member, role: newRole };
    handleChange(members);
  };

  return (

      <List spacing={4} direction="column" minW="80%" align="center" pb={5}>
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
