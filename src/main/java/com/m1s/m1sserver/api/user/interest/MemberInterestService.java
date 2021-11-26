package com.m1s.m1sserver.api.user.interest;


import com.m1s.m1sserver.api.interest.Interest;
import com.m1s.m1sserver.api.ranking.Ranking;
import com.m1s.m1sserver.api.ranking.RankingService;
import com.m1s.m1sserver.auth.member.Member;
import com.m1s.m1sserver.utils.CustomException;
import com.m1s.m1sserver.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberInterestService {
    @Autowired
    private MemberInterestRepository memberInterestRepository;

    @Autowired
    private RankingService rankingService;
    public MemberInterest createMemberInterest(Member member, Interest interest, Integer level){
        Ranking ranking;
        try {
            ranking = rankingService.getRanking(member, interest);
        }catch (CustomException e){
            ranking = rankingService.createRanking(member,interest);
        }

        return save(MemberInterest.builder()
                .member(member)
                .interest(interest)
                .level(level)
                .build());

    }

    public boolean checkOwner(Member member, MemberInterest memberInterest){
        if(member.getId() != memberInterest.getMemberId())throw new CustomException(ErrorCode.NO_AUTHENTICATION);
        return true;
    }

    public MemberInterest editLevel(Member member, MemberInterest memberInterest, Integer level){
        checkOwner(member, memberInterest);
        if(level != null)memberInterest.setLevel(level);
        return save(memberInterest);
    }

    public Iterable<MemberInterest> getMemberInterests(Member member){
        return memberInterestRepository.findAllByMemberId(member.getId());
    }

    public MemberInterest getMemberInterest(Long member_interest_id){
        if(!memberInterestRepository.existsById(member_interest_id))throw new CustomException(ErrorCode.MEMBER_INTEREST_NOT_FOUND);
        return memberInterestRepository.findById(member_interest_id).get();
    }

    public void deleteMemberInterest(Member member, MemberInterest memberInterest){
        checkOwner(member, memberInterest);
        if(!memberInterestRepository.existsById(memberInterest.getId()))throw new CustomException(ErrorCode.MEMBER_INTEREST_NOT_FOUND);
        memberInterestRepository.deleteById(memberInterest.getId());
    }
    public MemberInterest save(MemberInterest memberInterest){
        return memberInterestRepository.save(memberInterest);
    }

    public void deleteMemberInterests(Member member){
        memberInterestRepository.deleteAllByMemberId(member.getId());
    }

}
