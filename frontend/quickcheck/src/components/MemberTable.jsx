import React from 'react';
import { HStack, useColorModeValue, Heading, List } from '@chakra-ui/react';

//components
import AddButton from './AddMemberButton.jsx';
import RemoveButton from './RemoveMemberButton.jsx';
import MemberRow from './MemberRow.jsx';

// Assumption: ProjectMembers is a list of object: {id, role}
export default function MemberTable({ editMode, members, handleChange }) {
  const handleRemoveMember = (member) => () => {
    const newMembers = members.filter((m) => m.email !== member.email);
    handleChange(newMembers);
  };

  const handleAddMember = (newMember) => {
    handleChange([...members, newMember]);
  };

  const handleRoleChange = (member) => (newRole) => {
    // This is a curried function in JS
    let index = members.map((m) => m.email).indexOf(member.email);
    members[index] = { ...member, role: newRole };
    handleChange(members);
  };

  const bgHeading = useColorModeValue('gray.400', 'gray.500');
  return (
    <List spacing={2} direction="column" minW="80%" align="center" pb={5}>
      <HStack px={4} rounded="md" align="center" spacing={3} mb={5}>
        <Heading size="md" w="50%" shadow="lg" bg={bgHeading} p={2} pb={5} rounded="md">
          Email
        </Heading>
        <Heading size="md" minW={36} w={48} shadow="lg" bg={bgHeading} p={2} pb={5} rounded="md">
          Role
        </Heading>
        {editMode ? <AddButton w={16} onAddMember={handleAddMember} /> : <div />}
      </HStack>

      {members.map((member) => (
        <MemberRow
          key={member.email}
          member={member}
          editMode={editMode}
          onChangeRole={handleRoleChange(member)}
          removeButton={<RemoveButton onRemove={handleRemoveMember(member)} />}
        ></MemberRow>
      ))}
    </List>
  );
}
